package ru.kkeowki.financial_tracker.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int balance;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false, unique = true)
    private String billNumber;

    @OneToMany(mappedBy = "bill")
    @JsonBackReference
    @Builder.Default
    @ToString.Exclude
    private List<MoneyTransaction> transactions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @JsonManagedReference
    private CustomUser user;

    @Column
    private String bank;

    @Column
    private String info;

    private void isAvailableTransaction(MoneyTransaction transaction){
        if (transaction.getTransactionType() == TransactionType.OUT && balance < transaction.getAmount()){
            throw new IllegalArgumentException("На счёте недостаточно средств");
        }
    }

    private void rollbackTransaction(MoneyTransaction transaction){
        int rollbackAmount = transaction.getAmount();
        if (transaction.getTransactionType() == TransactionType.OUT){
            rollbackAmount *= -1;
        }
        balance -= rollbackAmount;
    }

    public void addTransaction(MoneyTransaction transaction){
        if (!currency.equals(transaction.getCurrency())){
            throw new IllegalArgumentException("Указанный счёт хранит валюту " + currency +
                    ", транзакция имеет валюту " + transaction.getCurrency());
        }
        isAvailableTransaction(transaction);
        int amount = transaction.getAmount();
        if (transaction.getTransactionType() == TransactionType.OUT){
            amount *= -1;
        }
        balance += amount;
        transactions.add(transaction);
        System.out.println("Транзакция сохранена в счёте с номером " + billNumber);
    }

    public void changeTransaction(MoneyTransaction transaction){
        if (!currency.equals(transaction.getCurrency())){
            throw new IllegalArgumentException("Указанный счёт хранит валюту " + currency +
                    ", транзакция имеет валюту " + transaction.getCurrency());
        }

        Optional<MoneyTransaction> changedTransactionOpt = transactions.stream()
                .filter(tr -> tr.getId().equals(transaction.getId()))
                .findFirst();

        if (changedTransactionOpt.isPresent()) {
            MoneyTransaction changedTransaction = changedTransactionOpt.get();

            rollbackTransaction(changedTransaction);

            isAvailableTransaction(transaction);
            int newAmount = transaction.getAmount();
            if (transaction.getTransactionType() == TransactionType.OUT){
                newAmount *= -1;
            }
            balance += newAmount;

            changedTransaction.setTransactionType(transaction.getTransactionType());
            changedTransaction.setAmount(transaction.getAmount());
            changedTransaction.setCurrency(transaction.getCurrency());
            changedTransaction.setSender(transaction.getSender());
            changedTransaction.setDate(transaction.getDate());
            changedTransaction.setTime(transaction.getTime());
            changedTransaction.setInfo(transaction.getInfo());
        } else {
            throw new IllegalArgumentException("Транзакция с id = " + transaction.getId() + " не найдена");
        }
    }

    public void deleteTransaction(Long id){
        Optional<MoneyTransaction> deletedTransactionOpt = transactions.stream()
                .filter(tr -> tr.getId().equals(id))
                .findFirst();
        if (deletedTransactionOpt.isPresent()){
            MoneyTransaction deletedTransaction = deletedTransactionOpt.get();
            rollbackTransaction(deletedTransaction);
            transactions.remove(deletedTransaction);
        } else {
            throw new IllegalArgumentException("Транзакция с id = " + id + " не найдена");
        }
    }
}
