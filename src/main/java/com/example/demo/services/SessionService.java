package com.example.demo.services;

import com.example.demo.model.Session;
import java.util.Optional;
import java.util.UUID;

public interface SessionService {
    Session createSession(UUID userId);

    Optional<Session> getSessionById(String sessionId);

    void deleteSession(String sessionId);

    void deleteExpiredSessions();

    boolean isSessionValid(String sessionId);
}
