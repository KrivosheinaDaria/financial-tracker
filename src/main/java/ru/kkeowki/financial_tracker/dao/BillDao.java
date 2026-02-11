package ru.kkeowki.financial_tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kkeowki.financial_tracker.dto.Bill;

public interface BillDao extends JpaRepository<Bill, Long> {
    void deleteByBillNumber(String billNumber);

    Bill findByBillNumber(String billNumber);

    @Modifying
    @Query("UPDATE Bill SET balance = :balance, currency = :currency, " +
            "billNumber = :bill_number, bank = :bank, info = :info " +
            "WHERE id = :id")
    int updateBill(
            @Param("id") Long id,
            @Param("balance") int balance,
            @Param("currency") String currency,
            @Param("bill_number") String billNumber,
            @Param("bank") String bank,
            @Param("info") String info
    );
}
