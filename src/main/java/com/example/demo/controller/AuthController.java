package com.example.demo.controller;

import com.example.demo.model.Session;
import com.example.demo.services.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SessionService sessionService;

    @PostMapping("/login")
    public String login(
            @RequestParam UUID userId,
            HttpServletResponse response
    ) {
        Session session = sessionService.createSession(userId);
        Cookie cookie = new Cookie("SESSIONID", session.getSessionId());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 4);
        response.addCookie(cookie);
        return "Login OK";
    }

    @PostMapping("/logout")
    public String logout(
            @CookieValue(value = "SESSIONID", required = false) String sessionId,
            HttpServletResponse response
    ) {
        if (sessionId != null) {
            sessionService.deleteSession(sessionId);
        }
        Cookie cookie = new Cookie("SESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "Logout OK";
    }
}
