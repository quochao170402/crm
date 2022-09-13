package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.RoleModel;
import cybersoft.javabackend.java18.crm.service.RoleService;
import cybersoft.javabackend.java18.crm.service.impl.RoleServiceImpl;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;
import cybersoft.javabackend.java18.crm.utils.common.ResponseHelper;
import cybersoft.javabackend.java18.crm.utils.common.ResponseModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "roleController",
        urlPatterns = {
                UrlUtils.ROLE,
        })
public class RoleController extends HttpServlet {
    private Gson gson;
    private RoleService roleService;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        roleService = RoleServiceImpl.getService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        Object object;
        boolean isSuccess = true;
        String message = "Find successfully";

        if (id == null || id.isBlank()) {
            object = processFindAll(req);
        } else {
            object = processFindOne(id);
            if (object == null) {
                isSuccess = false;
                message = "Not found role";
            }
        }
        System.out.println(req.getMethod());
        ResponseModel responseModel = ResponseHelper
                .toResponseModel(object, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    private RoleModel processFindOne(String id) {
        return roleService.findById(Integer.parseInt(id));
    }

    private List<RoleModel> processFindAll(HttpServletRequest req) {
        Map<String, String[]> requestParams = req.getParameterMap();

        int pageSize = Integer.parseInt(requestParams.get("pageSize") == null
                ? "10" : requestParams.get("pageSize")[0]);

        int currentPage = Integer.parseInt(requestParams.get("currentPage") == null
                ? "1" : requestParams.get("currentPage")[0]);

        return roleService.findAll(pageSize, currentPage);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleModel roleModel = getRoleModelFromRequest(req);

        boolean isSuccess = roleService.insert(roleModel);

        String message;
        if (isSuccess)
            message = "Insert successfully";
        else
            message = "Insert unsuccessfully";

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(null, isSuccess, message, resp.getStatus());

        returning(resp, responseModel);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleModel roleModel = getRoleModelFromRequest(req);
        boolean isSuccess = roleService.update(roleModel);

        String message;

        if (isSuccess)
            message = "Update successfully";
        else
            message = "Update unsuccessfully";

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(null, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String message;
        boolean isSuccess;
        if (id == null) {
            isSuccess = false;
            message = "Cannot delete role because id is null";
        } else {
            boolean result = roleService.delete(Integer.parseInt(id));
            if (result) {
                isSuccess = true;
                message = "Delete role successful";

            } else {
                isSuccess = false;
                message = "Delete role unsuccessful";
            }
        }
        ResponseModel responseModel = ResponseHelper
                .toResponseModel(null, isSuccess, message, resp.getStatus());

        returning(resp, responseModel);
    }

    private RoleModel getRoleModelFromRequest(HttpServletRequest req) throws IOException {
        RoleModel roleModel;
        try (BufferedReader reader = req.getReader()) {
            roleModel = gson.fromJson(reader, RoleModel.class);
        }
        return roleModel;
    }

    private void returning(HttpServletResponse response, ResponseModel responseModel) throws IOException {
        String json = gson.toJson(responseModel);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(json);
            writer.flush();
        }
    }
}
