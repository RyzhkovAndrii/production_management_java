package ua.com.novopacksv.production.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AngularHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestType = request.getHeader("Request-Type");
        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND
                && !"REST".equals(requestType)
                && !"/index.html".equals(request.getServletPath())) {
            request.getRequestDispatcher("/").forward(request, response);
        }
        return true;
    }
}
