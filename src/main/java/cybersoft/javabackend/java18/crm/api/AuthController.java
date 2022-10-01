package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.common.GsonHelper;
import cybersoft.javabackend.java18.crm.common.ResponseHelper;
import cybersoft.javabackend.java18.crm.common.ResponseModel;
import cybersoft.javabackend.java18.crm.model.ProjectModel;
import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.service.AuthService;
import cybersoft.javabackend.java18.crm.service.impl.AuthServiceImpl;
import cybersoft.javabackend.java18.crm.service.impl.ProjectServiceImpl;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "taskController",
        urlPatterns = {
//                UrlUtils.AUTH,
                UrlUtils.REGISTER,
                UrlUtils.LOGIN,
        })
public class AuthController extends HttpServlet {
    private AuthService authService;
    private Gson gson;


    @Override
    public void init() throws ServletException {
        super.init();
        authService = AuthServiceImpl.getInstance();
        gson = GsonHelper.getGson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        System.out.println(url);
        if (url.endsWith(UrlUtils.LOGIN)) doLogin(req, resp);
        else if (url.endsWith(UrlUtils.REGISTER)) doRegister(req, resp);
//                else
//                switch (url){
//                        case UrlUtils.LOGIN ->
//                        case UrlUtils.REGISTER -> doRegister(req, resp);
//                        default -> returning(resp, ResponseHelper.toErrorResponse("400", 400));
//                }
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserModel userModel = getRequestBody(req);
        UserModel loginUser = authService.login(userModel.getEmail(), userModel.getPassword());
        ResponseModel responseModel;

        if (loginUser == null) {
            responseModel = ResponseHelper
                    .toErrorResponse("404", 404);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", loginUser);
            responseModel = ResponseHelper
                    .toResponseModel(loginUser, 200);
        }

        returning(resp, responseModel);
    }

    private void doRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserModel userModel = getRequestBody(req);
        UserModel logged = authService.register(userModel);
        ResponseModel responseModel;

        if (logged == null)
            responseModel = ResponseHelper
                    .toErrorResponse("Register unsuccessful", 500);
        else
            responseModel = ResponseHelper
                    .toResponseModel(logged, 200);
        returning(resp, responseModel);

    }

    private UserModel getRequestBody(HttpServletRequest req) throws IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        return gson.fromJson(json, UserModel.class);
    }

    private void returning(HttpServletResponse response, ResponseModel responseModel) throws IOException {
        String json = gson.toJson(responseModel);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(json);
            writer.flush();
        }
    }
}
