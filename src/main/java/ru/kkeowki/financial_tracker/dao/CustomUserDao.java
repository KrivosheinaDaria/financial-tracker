package ru.kkeowki.financial_tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kkeowki.financial_tracker.dto.CustomUser;

import java.util.Optional;

public interface CustomUserDao extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByUsername(String username);
}
