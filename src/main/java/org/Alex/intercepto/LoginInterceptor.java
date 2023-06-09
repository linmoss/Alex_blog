package org.Alex.intercepto;

import org.Alex.common.WebSite;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Boolean sign = (Boolean) session.getAttribute(WebSite.LOGIN_SIGN);
        if (Objects.nonNull(sign) && sign) {
            // 已登录
            return true;
        } else {
            request.setAttribute("errorMsg", "请登录后进行操作");
            request.getRequestDispatcher("/admin/login.html").forward(request, response);
            return false;
        }
    }
}
