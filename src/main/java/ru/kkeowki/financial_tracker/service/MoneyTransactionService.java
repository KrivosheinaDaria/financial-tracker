package ru.kkeowki.financial_tracker.service;

import ru.kkeowki.financial_tracker.dto.MoneyTransaction;

import java.util.List;
import java.util.Optional;

public interface MoneyTransactionService {
    List<MoneyTransaction> findAll();

    MoneyTransaction saveMoneyTransaction(MoneyTransaction transaction);

    Optional<MoneyTransaction> findById(Long id);

    int updateMoneyTransaction(MoneyTransaction transaction);

    void deleteById(Long id);
}
