package ru.kkeowki.financial_tracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kkeowki.financial_tracker.dto.Bill;
import ru.kkeowki.financial_tracker.service.BillService;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
@AllArgsConstructor
public class BillController {
    private BillService service;

    @GetMapping
    public List<Bill> findAll(){
        return service.findAll();
    }

    @PostMapping("save_bill")
    public Bill saveBill(@RequestBody Bill bill){
        return service.saveBill(bill);
    }

    @GetMapping("/{bill_number}")
    public Bill findBillByBillNumber(@PathVariable("bill_number") String billNumber){
        return service.findByBillNumber(billNumber);
    }

    @PutMapping("update_bill")
    public int updateBill(@RequestBody Bill bill){
        return service.updateBill(bill);
    }

    @DeleteMapping("delete_bill/{bill_number}")
    public void deleteBillByBillNumber(@PathVariable("bill_number") String billNumber){
        service.deleteByBillNumber(billNumber);
    }
}
