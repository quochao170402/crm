package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.StatusModel;
import cybersoft.javabackend.java18.crm.service.StatusService;
import cybersoft.javabackend.java18.crm.service.impl.StatusServiceImpl;
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

@WebServlet(name = "statusController",
        urlPatterns = {
                UrlUtils.STATUS
        })
public class StatusController extends HttpServlet {

    private Gson gson;
    private StatusService statusService;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = GsonHelper.getGson();
        statusService = StatusServiceImpl.getService();
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
            object = processFindOne(Integer.parseInt((id)));
            if (object == null) {
                isSuccess = false;
                message = "Not found status";
            }
        }
        ResponseModel responseModel = ResponseHelper.toResponseModel(object, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatusModel statusModel = getRequestBody(req);
        boolean isSuccess = statusService.insert(statusModel);
        String message;
        if (isSuccess) {
            message = "Insert successfully";
        } else {
            message = "Insert unsuccessfully";
        }
        ResponseModel responseModel = ResponseHelper
                .toResponseModel(statusModel, isSuccess, message, resp.getStatus());

        returning(resp, responseModel);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatusModel statusModel = getRequestBody(req);
        boolean isSuccess = statusService.update(statusModel);
        String message;
        if (isSuccess) {
            message = "Update successfully";
        } else {
            message = "Update unsuccessfully";
        }
        ResponseModel responseModel = ResponseHelper
                .toResponseModel(statusModel, isSuccess, message, resp.getStatus());

        returning(resp, responseModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        boolean isSuccess;
        String message;
        if (id == null) {
            isSuccess = false;
            message = "Cannot delete status because id is null";
        } else {
            isSuccess = statusService.delete(Integer.parseInt(id));
            if (isSuccess) {
                message = "Delete successfully";
            } else {
                message = "Delete unsuccessfully";
            }
        }
        ResponseModel responseModel = ResponseHelper
                .toResponseModel(null, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    private Object processFindOne(int id) {
        return statusService.findById(id);
    }

    private Object processFindAll(HttpServletRequest req) {
        Map<String, String[]> requestParams = req.getParameterMap();

        int pageSize = Integer.parseInt(requestParams.get("pageSize") == null
                ? "10" : requestParams.get("pageSize")[0]);

        int currentPage = Integer.parseInt(requestParams.get("currentPage") == null
                ? "1" : requestParams.get("currentPage")[0]);

        return statusService.findAll(pageSize, currentPage);
    }

    private StatusModel getRequestBody(HttpServletRequest req) throws IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        return gson.fromJson(json, StatusModel.class);
    }

    private void returning(HttpServletResponse response, ResponseModel responseModel) throws IOException {
        String json = gson.toJson(responseModel);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(json);
            writer.flush();
        }
    }
}
