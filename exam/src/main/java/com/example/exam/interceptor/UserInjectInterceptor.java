package com.example.exam.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class UserInjectInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView mv) throws Exception {

        if (mv == null) return;

        HttpSession session = request.getSession(false);
        // 이미 존재하는 세션만 get 하고 없으면 null 반환
        if (session == null) return;

        Object user = session.getAttribute("User");
        if (user != null) {
            mv.addObject("user", user);
            log.info("Model에 user 주입 완료");
        }
    }
}

