package ru.kkeowki.financial_tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kkeowki.financial_tracker.dto.Bill;
import ru.kkeowki.financial_tracker.dto.MoneyTransaction;
import ru.kkeowki.financial_tracker.dto.TransactionType;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MoneyTransactionDao extends JpaRepository<MoneyTransaction, Long> {
    @Modifying
    @Query("UPDATE MoneyTransaction SET transactionType = :transaction_type, " +
            "amount = :amount, currency = :currency, sender = :sender, date = :date, time = :time, " +
            "info = :info, bill = :bill WHERE id = :id AND bill.billNumber IN (SELECT billNumber FROM Bill)")
    int updateMoneyTransaction(
            @Param("id") Long id,
            @Param("transaction_type") TransactionType transactionType,
            @Param("amount") int amount,
            @Param("bill") Bill bill,
            @Param("currency") String currency,
            @Param("sender") String sender,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time,
            @Param("info") String info
            );
}
