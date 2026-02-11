package ru.kkeowki.financial_tracker.service;

import ru.kkeowki.financial_tracker.dto.CustomUser;

import java.util.List;

public interface CustomUserService {
    List<CustomUser> findAll();

    CustomUser saveUser(CustomUser user);
}
