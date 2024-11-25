package com.example.demo.filters;

import com.example.demo.services.SessionService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class SessionFilter implements Filter {

    private final SessionService sessionService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    String sessionId = cookie.getValue();
                    if (sessionService.isSessionValid(sessionId)) {
                        sessionService.getSessionById(sessionId).ifPresent(
                                session -> System.out.println(
                                        "Valid session for user: " + session.getUserId()
                                )
                        );
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
