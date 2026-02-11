package ru.kkeowki.financial_tracker.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransactionType {
    IN("Поступление"),
    OUT("Списание");

    private final String description;
}
