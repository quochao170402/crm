package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.dto.TaskDto;
import cybersoft.javabackend.java18.crm.mapper.TaskMapper;
import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.service.TaskService;
import cybersoft.javabackend.java18.crm.service.impl.TaskServiceImpl;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;
import cybersoft.javabackend.java18.crm.utils.common.GsonHelper;
import cybersoft.javabackend.java18.crm.utils.common.ResponseHelper;
import cybersoft.javabackend.java18.crm.utils.common.ResponseModel;

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
                UrlUtils.TASK,
        })
public class TaskController extends HttpServlet {
    private TaskService taskService = null;
    private Gson gson = null;

    private TaskMapper mapper;

    @Override
    public void init() throws ServletException {
        super.init();
        taskService = TaskServiceImpl.getService();
        gson = GsonHelper.getGson();
        mapper = TaskMapper.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Object object;
        boolean isSuccess = true;
        String message = "Find successfully";
        if (id != null && !"".equals(id.trim())) {
            object = processFindOne(Integer.parseInt(id));
            if (object == null) {
                isSuccess = false;
                message = "Not found task";
            }
        } else {
            object = processFindAll(req);
        }

        ResponseModel responseModel = ResponseHelper.toResponseModel(object, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskModel taskModel = getRequestBody(req);
        boolean isSuccess = taskService.insert(taskModel);
        String message = isSuccess ? "Inert successfully" : "Insert unsuccessfully";
        ResponseModel responseModel = ResponseHelper.toResponseModel(taskModel, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskModel taskModel = getRequestBody(req);
        boolean isSuccess = taskService.update(taskModel);
        String message = isSuccess ? "Update successfully" : "Update unsuccessfully";
        ResponseModel responseModel = ResponseHelper.toResponseModel(taskModel, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        boolean isSuccess;
        String message;
        if (id != null && !"".equals(id.trim())) {
            isSuccess = taskService.deleteById(Integer.parseInt(id));
            if (isSuccess) {
                message = "Delete successfully";
            } else {
                message = "Delete unsuccessfully";
            }
        } else {
            isSuccess = false;
            message = "Delete unsuccessfully because id is null";
        }
        ResponseModel responseModel = ResponseHelper.toResponseModel(null, isSuccess, message, resp.getStatus());
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

    private TaskModel getRequestBody(HttpServletRequest req) throws IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        return gson.fromJson(json, TaskModel.class);
    }

    private void returning(HttpServletResponse response, ResponseModel responseModel) throws IOException {
        String json = gson.toJson(responseModel);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(json);
            writer.flush();
        }
    }

}
