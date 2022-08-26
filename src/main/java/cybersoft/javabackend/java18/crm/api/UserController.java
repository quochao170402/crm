package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.dto.UserDetailDto;
import cybersoft.javabackend.java18.crm.dto.UserDto;
import cybersoft.javabackend.java18.crm.mapper.UserMapper;
import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.service.UserService;
import cybersoft.javabackend.java18.crm.service.impl.UserServiceImpl;
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

@WebServlet(name = "userController",
        urlPatterns = {
                UrlUtils.USER,
        })
public class UserController extends HttpServlet {
    private UserService userService;
    private Gson gson;
    private UserMapper userMapper;


    @Override
    public void init() throws ServletException {
        super.init();
        userService = UserServiceImpl.getService();
        gson = GsonHelper.getGson();
        userMapper = UserMapper.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Object obj;
        boolean isSuccess = true;
        String message = "Find successfully";
        if (id == null || "".equals(id)) {
            obj = processingFindAll(req);
        } else {
            obj = processingFindOne(Integer.parseInt(id));
            if (obj == null) {
                isSuccess = false;
                message = "Not found user";
            }
        }

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(obj, isSuccess, message, resp.getStatus());

        returning(resp, responseModel);

    }

    private List<UserDto> processingFindAll(HttpServletRequest req) {
        Map<String, String[]> requestParams = req.getParameterMap();

        int pageSize = Integer.parseInt(requestParams.get("pageSize") == null
                ? "10" : requestParams.get("pageSize")[0]);

        int currentPage = Integer.parseInt(requestParams.get("currentPage") == null
                ? "1" : requestParams.get("currentPage")[0]);

        List<UserDto> dtos = new ArrayList<>();
        userService.findAll(pageSize, currentPage)
                .forEach(model -> dtos.add(userMapper.toDto(model)));
        return dtos;
    }

    private UserDetailDto processingFindOne(int id) {
        return userMapper.toUserDetailDto(userService.findById(id));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getRequestBody(req);
        boolean isSuccess = userService.insert(user);

        String message;
        if (isSuccess)
            message = "Insert successfully";
        else message = "Insert unsuccessfully";

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(user, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getRequestBody(req);
        boolean isSuccess = userService.update(user);

        String message;
        if (isSuccess)
            message = "Update successfully";
        else message = "Update unsuccessfully";

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(user, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        boolean isSuccess;
        String message;
        if (id == null) {
            isSuccess = false;
            message = "Cannot delete user because id is null";
        } else {
            if (userService.delete(Integer.parseInt(id))) {
                isSuccess = true;
                message = "Delete successfully";
            } else {
                isSuccess = false;
                message = "Delete unsuccessfully";
            }
        }

        ResponseModel responseModel = ResponseHelper
                .toResponseModel(id, isSuccess, message, resp.getStatus());
        returning(resp, responseModel);
    }

    private UserModel getRequestBody(HttpServletRequest req) throws IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        return gson.fromJson(json, UserModel.class);
    }

    private void returning(HttpServletResponse resp, ResponseModel responseModel) throws IOException {
        String json = gson.toJson(responseModel);
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(json);
            writer.flush();
        }
    }
}
