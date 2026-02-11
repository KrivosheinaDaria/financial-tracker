package ru.kkeowki.financial_tracker.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kkeowki.financial_tracker.dao.BillDao;
import ru.kkeowki.financial_tracker.dao.CustomUserDao;
import ru.kkeowki.financial_tracker.dto.Bill;
import ru.kkeowki.financial_tracker.dto.CustomUser;
import ru.kkeowki.financial_tracker.service.BillService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {
    private BillDao billDao;
    private CustomUserDao userDao;

    @Override
    public List<Bill> findAll() {
        return billDao.findAll();
    }

    @Override
    public Bill saveBill(Bill bill) {
        String username = bill.getUser().getUsername();
        Optional<CustomUser> userOp = userDao.findByUsername(username);
        if (userOp.isPresent()){
            CustomUser user = userOp.get();
            bill.setUser(user);
            user.getBills().add(bill);
            return billDao.save(bill);
        } else {
            throw new UsernameNotFoundException("Пользователь с логином " + username + " не найден");
        }
    }

    @Override
    public Bill findByBillNumber(String billNumber) {
        return billDao.findByBillNumber(billNumber);
    }

    @Override
    @Transactional
    public int updateBill(Bill bill) {
        return billDao.updateBill(
                bill.getId(),
                bill.getBalance(),
                bill.getCurrency(),
                bill.getBillNumber(),
                bill.getBank(),
                bill.getInfo()
        );
    }

    @Override
    @Transactional
    public void deleteByBillNumber(String billNumber) {
        billDao.deleteByBillNumber(billNumber);
    }
}
