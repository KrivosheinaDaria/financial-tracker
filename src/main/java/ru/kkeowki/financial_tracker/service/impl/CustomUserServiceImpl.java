package ru.kkeowki.financial_tracker.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kkeowki.financial_tracker.dao.CustomUserDao;
import ru.kkeowki.financial_tracker.dto.CustomUser;
import ru.kkeowki.financial_tracker.service.CustomUserService;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserServiceImpl implements CustomUserService {
    private CustomUserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<CustomUser> findAll() {
        return userDao.findAll();
    }

    @Override
    public CustomUser saveUser(CustomUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }
}
