package com.example.exam.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        log.info("preHandle 실행, uri={}", request.getRequestURI());

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("User") == null) {
            response.sendRedirect("/member/login?msg=required");
            return false; // 컨트롤러 진입 차단
        }

        return true; // 컨트롤러 실행 허용
    }


}
