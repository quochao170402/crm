package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.common.GsonHelper;
import cybersoft.javabackend.java18.crm.common.ResponseHelper;
import cybersoft.javabackend.java18.crm.common.ResponseModel;
import cybersoft.javabackend.java18.crm.model.StatusModel;
import cybersoft.javabackend.java18.crm.service.StatusService;
import cybersoft.javabackend.java18.crm.service.impl.StatusServiceImpl;
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
        StatusModel statusModel = getStatusFromRequest(req);

        ResponseModel responseModel;

        if (statusService.insert(statusModel)) {
            responseModel = ResponseHelper
                    .toResponseModel(statusModel, resp.getStatus());
        } else {
            responseModel = ResponseHelper
                    .toErrorResponse("Insert status unsuccessful", resp.getStatus());
        }

        returning(resp, responseModel);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatusModel statusModel = getStatusFromRequest(req);

        ResponseModel responseModel;

        if (statusService.update(statusModel)) {
            responseModel = ResponseHelper
                    .toResponseModel(statusModel, resp.getStatus());
        } else {
            responseModel = ResponseHelper
                    .toErrorResponse("Update status unsuccessful", resp.getStatus());
        }

        returning(resp, responseModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ResponseModel responseModel;
        if (id == null) {
            responseModel = ResponseHelper
                    .toErrorResponse("Cannot delete status, because id is null", 400);
        } else {
            if (statusService.delete(Integer.parseInt(id))) {
                responseModel = ResponseHelper
                        .toResponseModel("Delete status successful", resp.getStatus());
            } else {
                responseModel = ResponseHelper
                        .toErrorResponse("Delete status unsuccessful", resp.getStatus());
            }
        }
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

    private StatusModel getStatusFromRequest(HttpServletRequest req) throws IOException {
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
