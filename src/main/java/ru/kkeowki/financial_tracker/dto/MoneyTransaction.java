package ru.kkeowki.financial_tracker.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "money_transaction")
public class MoneyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    @ToString.Exclude
    private Bill bill;

    @Column(nullable = false)
    private String currency;

    @Column
    private String sender;

    @Column
    private LocalDate date;

    @Column
    private LocalTime time;

    @Column
    private String info;

}
