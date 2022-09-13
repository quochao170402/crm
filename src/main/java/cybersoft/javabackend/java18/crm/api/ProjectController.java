package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.mapper.ProjectMapper;
import cybersoft.javabackend.java18.crm.model.ProjectModel;
import cybersoft.javabackend.java18.crm.service.ProjectService;
import cybersoft.javabackend.java18.crm.service.impl.ProjectServiceImpl;
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
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "projectController",
        urlPatterns = {
                UrlUtils.PROJECT
        })
public class ProjectController extends HttpServlet {
    private ProjectService projectService;
    private Gson gson;


    @Override
    public void init() throws ServletException {
        super.init();
        projectService = ProjectServiceImpl.getService();
        gson = GsonHelper.getGson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        Object object;
        boolean isSuccess = true;
        String message = "Find successfully";
        if (id == null || "".equals(id)) {
            object = processFindAll(req);
        } else {
            object = processFindOne(id);
            if (object == null) {
                isSuccess = false;
                message = "Not found project";
            }
        }

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(object, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectModel projectModel = getRequestBody(req);
        System.out.println(projectModel);
        boolean isSuccess = projectService.insert(projectModel);
        String message;
        if (isSuccess)
            message = "Insert successfully";
        else
            message = "Insert unsuccessfully";

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(projectModel, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectModel projectModel = getRequestBody(req);
        boolean isSuccess = projectService.update(projectModel);
        String message;
        if (isSuccess)
            message = "Update successfully";
        else
            message = "Update unsuccessfully";

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(projectModel, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String message;
        boolean isSuccess;
        if (id == null) {
            isSuccess = false;
            message = "Cannot delete project because id is null";
        } else {
            boolean result = projectService.delete(Integer.parseInt(id));
            if (result) {
                isSuccess = true;
                message = "Delete successful";

            } else {
                isSuccess = false;
                message = "Delete unsuccessful";
            }
        }
        ResponseModel responseModel = ResponseHelper.toResponseModel(null, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    private Object processFindOne(String id) {
        ProjectModel projectModel = projectService.findById(Integer.parseInt(id));
        return ProjectMapper.getInstance().toProjectDetailDto(projectModel);
    }

    private Object processFindAll(HttpServletRequest req) {
        Map<String, String[]> requestParams = req.getParameterMap();

        int pageSize = Integer.parseInt(requestParams.get("pageSize") == null
                ? "10" : requestParams.get("pageSize")[0]);

        int currentPage = Integer.parseInt(requestParams.get("currentPage") == null
                ? "1" : requestParams.get("currentPage")[0]);

        return projectService.findAll(pageSize, currentPage);
    }

    private ProjectModel getRequestBody(HttpServletRequest req) throws IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        return gson.fromJson(json, ProjectModel.class);
    }

    private void returning(HttpServletResponse response, ResponseModel responseModel) throws IOException {
        String json = gson.toJson(responseModel);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(json);
            writer.flush();
        }
    }
}
