package ru.kkeowki.financial_tracker.service;

import ru.kkeowki.financial_tracker.dto.Bill;

import java.util.List;

public interface BillService {
    List<Bill> findAll();

    Bill saveBill(Bill bill);

    Bill findByBillNumber(String billNumber);

    int updateBill(Bill bill);

    void deleteByBillNumber(String billNumber);
}
