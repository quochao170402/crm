package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.common.GsonHelper;
import cybersoft.javabackend.java18.crm.common.ResponseHelper;
import cybersoft.javabackend.java18.crm.common.ResponseModel;
import cybersoft.javabackend.java18.crm.dto.TaskDto;
import cybersoft.javabackend.java18.crm.mapper.TaskMapper;
import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.service.TaskService;
import cybersoft.javabackend.java18.crm.service.impl.TaskServiceImpl;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "taskController",
        urlPatterns = {
                "/api/tasks"
        })
public class TaskController extends HttpServlet {
    private TaskService taskService = null;
    private Gson gson = null;

    private TaskMapper mapper;

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("Task servlet");
        taskService = TaskServiceImpl.getService();
        gson = GsonHelper.getGson();
        mapper = TaskMapper.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        Object object;
        if (id == null || "".equals(id)) {
            object = processFindAll(req);
        } else {
            object = processFindOne(Integer.parseInt(id));
        }

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(object, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        TaskDto taskDto = getTaskFromRequest(req);
        TaskModel taskModel = TaskMapper.getInstance().toModel(taskDto);
        ResponseModel responseModel;

        if (taskService.insert(taskModel)) {
            responseModel = ResponseHelper
                    .toResponseModel(taskDto, resp.getStatus());
        } else {
            responseModel = ResponseHelper
                    .toErrorResponse("Insert task unsuccessful", resp.getStatus());
        }

        returning(resp, responseModel);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskDto taskDto = getTaskFromRequest(req);
        TaskModel taskModel = TaskMapper.getInstance().toModel(taskDto);

        ResponseModel responseModel;

        if (taskService.update(taskModel)) {
            responseModel = ResponseHelper
                    .toResponseModel(taskDto, resp.getStatus());
        } else {
            responseModel = ResponseHelper
                    .toErrorResponse("Update task unsuccessful", resp.getStatus());
        }

        returning(resp, responseModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ResponseModel responseModel;
        if (id == null) {
            responseModel = ResponseHelper
                    .toErrorResponse("Cannot delete task, because id is null", 400);
        } else {
            if (taskService.delete(Integer.parseInt(id))) {
                responseModel = ResponseHelper
                        .toResponseModel("Delete task successful", resp.getStatus());
            } else {
                responseModel = ResponseHelper
                        .toErrorResponse("Delete task unsuccessful", resp.getStatus());
            }
        }
        returning(resp, responseModel);
    }

    private Object processFindOne(int id) {
        return mapper.toDto(taskService.findById(id));
    }

    private Object processFindAll(HttpServletRequest req) {
        Map<String, String[]> requestParams = req.getParameterMap();

        int pageSize = Integer.parseInt(requestParams.get("pageSize") == null
                ? "10" : requestParams.get("pageSize")[0]);

        int currentPage = Integer.parseInt(requestParams.get("currentPage") == null
                ? "1" : requestParams.get("currentPage")[0]);
        List<TaskDto> dtos = new ArrayList<>();
        taskService.findAll(pageSize, currentPage).forEach(task -> dtos.add(mapper.toDto(task)));
        return dtos;
    }

    private TaskDto getTaskFromRequest(HttpServletRequest req) throws IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        System.out.println(json);
        return gson.fromJson(json, TaskDto.class);
    }

    private void returning(HttpServletResponse response, ResponseModel responseModel) throws IOException {
        String json = gson.toJson(responseModel);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(json);
            writer.flush();
        }
    }

}
