package cybersoft.javabackend.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.service.UserService;
import cybersoft.javabackend.java18.crm.service.impl.UserServiceImpl;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userController",
        urlPatterns = {
                UrlUtils.USER,
        })
public class UserController extends HttpServlet {

    private UserService userService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = UserServiceImpl.getService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = gson.toJson(userService.findAll());
    }
}
