package com.example.demo.repositories;

import com.example.demo.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionId(String sessionId);
    void deleteAllByExpiresAtBefore(LocalDateTime now);
    void deleteBySessionId(String sessionId);
}
