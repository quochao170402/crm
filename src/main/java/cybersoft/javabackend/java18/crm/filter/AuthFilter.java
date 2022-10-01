package cybersoft.javabackend.java18.crm.filter;

import com.google.gson.Gson;
import cybersoft.javabackend.java18.crm.common.ResponseHelper;
import cybersoft.javabackend.java18.crm.common.ResponseModel;
import cybersoft.javabackend.java18.crm.utils.UrlUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = {UrlUtils.ALL})
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isAuthUrl(request) || isAuthenticated(request)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            ResponseModel responseModel = ResponseHelper.toErrorResponse("Unauthorized", 401);
            returning(response, responseModel);
        }
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute("currentUser") != null;

    }

    private boolean isAuthUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.endsWith("login") || uri.equals("register");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void returning(HttpServletResponse response, ResponseModel responseModel) throws IOException {
        String json = new Gson().toJson(responseModel);
        try (PrintWriter writer = response.getWriter()) {
            writer.println(json);
            writer.flush();
        }
    }
}
