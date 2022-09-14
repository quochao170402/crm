package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.common.GsonHelper;
import cybersoft.javabackend.java18.crm.common.ResponseHelper;
import cybersoft.javabackend.java18.crm.common.ResponseModel;
import cybersoft.javabackend.java18.crm.mapper.ProjectMapper;
import cybersoft.javabackend.java18.crm.model.ProjectModel;
import cybersoft.javabackend.java18.crm.service.ProjectService;
import cybersoft.javabackend.java18.crm.service.impl.ProjectServiceImpl;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

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
        if (id == null || "".equals(id)) {
            object = processFindAll(req);
        } else {
            object = processFindOne(id);
        }

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(object, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ProjectModel projectModel = getRequestBody(req);

        ResponseModel responseModel;

        if (projectService.insert(projectModel)) {
            responseModel = ResponseHelper
                    .toResponseModel(projectModel, resp.getStatus());
        } else {
            responseModel = ResponseHelper
                    .toErrorResponse("Insert project unsuccessful", resp.getStatus());
        }

        returning(resp, responseModel);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProjectModel projectModel = getRequestBody(req);

        ResponseModel responseModel;

        if (projectService.update(projectModel)) {
            responseModel = ResponseHelper
                    .toResponseModel(projectModel, resp.getStatus());
        } else {
            responseModel = ResponseHelper
                    .toErrorResponse("Update project unsuccessful", resp.getStatus());
        }

        returning(resp, responseModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ResponseModel responseModel;
        if (id == null) {
            responseModel = ResponseHelper
                    .toErrorResponse("Cannot delete project, because id is null", 400);
        } else {
            if (projectService.delete(Integer.parseInt(id))) {
                responseModel = ResponseHelper
                        .toResponseModel("Delete project successful", resp.getStatus());
            } else {
                responseModel = ResponseHelper
                        .toErrorResponse("Delete project unsuccessful", resp.getStatus());
            }
        }
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
