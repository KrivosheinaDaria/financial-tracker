package ru.kkeowki.financial_tracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kkeowki.financial_tracker.dto.CustomUser;
import ru.kkeowki.financial_tracker.service.CustomUserService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private CustomUserService service;

    @GetMapping
    public List<CustomUser> findAll(){
        return service.findAll();
    }

    @PostMapping("save_user")
    public CustomUser saveUser(@RequestBody CustomUser user){
        return service.saveUser(user);
    }
}
