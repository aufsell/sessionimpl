package com.example.demo.services;

import com.example.demo.model.Session;
import com.example.demo.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public Session createSession(UUID userId) {
        String sessionId = generateSessionId();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusHours(1);

        Session session = new Session(sessionId, userId, now, expiresAt);
        return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> getSessionById(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionRepository.deleteBySessionId(sessionId);
    }

    @Override
    public void deleteExpiredSessions() {
        sessionRepository.deleteAllByExpiresAtBefore(LocalDateTime.now());
    }

    @Override
    public boolean isSessionValid(String sessionId) {
        Optional<Session> sessionOpt = getSessionById(sessionId);
        return sessionOpt.filter(session -> session.getExpiresAt().isAfter(LocalDateTime.now())).isPresent();
    }

    private String generateSessionId() {
        return Long.toHexString(ThreadLocalRandom.current().nextLong()) + UUID.randomUUID();
    }
}
