package ru.kkeowki.financial_tracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kkeowki.financial_tracker.dto.MoneyTransaction;
import ru.kkeowki.financial_tracker.service.MoneyTransactionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class MoneyTransactionController {
    private MoneyTransactionService service;

    @GetMapping
    public List<MoneyTransaction> findAll(){
        return service.findAll();
    }

    @PostMapping("save_transaction")
    public MoneyTransaction saveMoneyTransaction(@RequestBody MoneyTransaction transaction){
        return service.saveMoneyTransaction(transaction);
    }

    @GetMapping("/{id}")
    public Optional<MoneyTransaction> findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("update_transaction")
    public int updateMoneyTransaction(@RequestBody MoneyTransaction transaction){
        return service.updateMoneyTransaction(transaction);
    }

    @DeleteMapping("delete_mapping/{id}")
    public void deleteById(@PathVariable Long id){
        service.deleteById(id);
    }
}
