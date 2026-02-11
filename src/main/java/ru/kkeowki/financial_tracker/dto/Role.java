package ru.kkeowki.financial_tracker.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    USER("Пользователь"),
    ADMIN("Администратор");

    private final String description;
}
