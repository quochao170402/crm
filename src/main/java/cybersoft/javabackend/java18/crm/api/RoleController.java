package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.model.RoleModel;
import cybersoft.javabackend.java18.crm.service.RoleService;
import cybersoft.javabackend.java18.crm.service.impl.RoleServiceImpl;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

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

        String json = gson.toJson(roleService.findAll());
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(json);
            writer.flush();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleModel roleModel = getRoleModelFromRequest(req);
        try (PrintWriter writer = resp.getWriter()) {
            String json;
            if (roleService.insert(roleModel)) {
                json = gson.toJson(roleService.findAll());
            } else {
                json = gson.toJson(null);
            }
            writer.println(json);
            writer.flush();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roleId = req.getParameter("id");
        if (roleId != null) {
            int id = Integer.parseInt(roleId);
            RoleModel roleModel = getRoleModelFromRequest(req);
            try (PrintWriter writer = resp.getWriter()) {
                String json;
                if (roleService.update(id, roleModel)) {
                    json = gson.toJson(roleService.findAll());

                } else {
                    json = gson.toJson(null);
                }
                writer.println(json);
                writer.flush();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roleId = req.getParameter("id");
        if (roleId != null) {
            int id = Integer.parseInt(roleId);
            try (PrintWriter writer = resp.getWriter()) {
                String json;
                if (roleService.delete(id)) {
                    json = gson.toJson(roleService.findAll());

                } else {
                    json = gson.toJson(null);
                }
                writer.println(json);
                writer.flush();
            }
        }
    }

    private RoleModel getRoleModelFromRequest(HttpServletRequest req) throws IOException {
        RoleModel roleModel;
        try (BufferedReader reader = req.getReader()) {
            String requestBody = reader.lines().collect(Collectors.joining());
            roleModel = gson.fromJson(requestBody, RoleModel.class);
        }
        return roleModel;
    }
}
