package ru.kkeowki.financial_tracker.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kkeowki.financial_tracker.dao.BillDao;
import ru.kkeowki.financial_tracker.dao.MoneyTransactionDao;
import ru.kkeowki.financial_tracker.dto.Bill;
import ru.kkeowki.financial_tracker.dto.MoneyTransaction;
import ru.kkeowki.financial_tracker.service.MoneyTransactionService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MoneyTransactionServiceImpl implements MoneyTransactionService {
    private MoneyTransactionDao moneyTransactionDao;
    private BillDao billDao;

    @Override
    public List<MoneyTransaction> findAll() {
        return moneyTransactionDao.findAll();
    }

    @Override
    public MoneyTransaction saveMoneyTransaction(MoneyTransaction transaction) {
        String billNumber = transaction.getBill().getBillNumber();
        Bill bill = billDao.findByBillNumber(billNumber);
        bill.addTransaction(transaction);
        transaction.setBill(bill);
        return moneyTransactionDao.save(transaction);
    }

    @Override
    public Optional<MoneyTransaction> findById(Long id) {
        return moneyTransactionDao.findById(id);
    }

    @Override
    @Transactional
    public int updateMoneyTransaction(MoneyTransaction transaction) {
        Optional<MoneyTransaction> changedTransactionOp = findById(transaction.getId());
        if (changedTransactionOp.isPresent()){
            System.out.println("Транзакция для удаления найдена");
            MoneyTransaction changedTransaction = changedTransactionOp.get();

            if (transaction.getBill().getBillNumber().equals(changedTransaction.getBill().getBillNumber())){
                String billNumber = transaction.getBill().getBillNumber();
                Bill bill = billDao.findByBillNumber(billNumber);
                bill.changeTransaction(transaction);
            } else {
                String oldBillNumber = changedTransaction.getBill().getBillNumber();
                Bill oldBill = billDao.findByBillNumber(oldBillNumber);
                oldBill.deleteTransaction(changedTransaction.getId());

                String newBillNumber = transaction.getBill().getBillNumber();
                Bill newBill = billDao.findByBillNumber(newBillNumber);
                newBill.addTransaction(transaction);
            }

            return moneyTransactionDao.updateMoneyTransaction(
                    transaction.getId(),
                    transaction.getTransactionType(),
                    transaction.getAmount(),
                    transaction.getBill(),
                    transaction.getCurrency(),
                    transaction.getSender(),
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getInfo()
            );
        }
        throw new IllegalArgumentException("Транзакция с id = " + transaction.getId() + " не найдена");
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<MoneyTransaction> transactionOp = findById(id);
        if (transactionOp.isPresent()) {
            MoneyTransaction transaction = transactionOp.get();

            String billNumber = transaction.getBill().getBillNumber();
            Bill bill = billDao.findByBillNumber(billNumber);
            bill.deleteTransaction(id);
            moneyTransactionDao.deleteById(id);
        } else {
            throw new IllegalArgumentException("Транзакция с id = " + id + " не найдена");
        }
    }
}
