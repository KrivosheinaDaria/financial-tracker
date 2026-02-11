package ru.kkeowki.financial_tracker.service.impl;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kkeowki.financial_tracker.dao.CustomUserDao;
import ru.kkeowki.financial_tracker.dto.CustomUser;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private CustomUserDao userDao;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CustomUser> userOp = userDao.findByUsername(username);
        if (userOp.isPresent()) {
            return userOp.get();
        }
        throw new UsernameNotFoundException("Пользователь с логином " + username + " не найден");
    }
}
