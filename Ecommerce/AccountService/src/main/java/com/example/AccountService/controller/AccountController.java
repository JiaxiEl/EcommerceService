package com.example.AccountService.controller;

import com.example.AccountService.dto.AccountDto;
import com.example.AccountService.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.AccountService.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final JwtUtil jwtUtil;
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AccountDto> registerAccount(@RequestBody AccountDto accountDto) {
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email")
    public ResponseEntity<AccountDto> getAccountByEmail(@RequestParam String email) {
        return accountService.getAccountByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");

        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<AccountDto> updatedAccountOpt = accountService.updateAccountPassword(id, password);
        return updatedAccountOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AccountDto loginDto) {
        Optional<AccountDto> accountOpt = accountService.getAccountByEmail(loginDto.getEmail());

        if (accountOpt.isPresent()) {
            AccountDto account = accountOpt.get();

            if (accountService.verifyPassword(loginDto.getPassword(), account.getPassword())) {
                String token = jwtUtil.generateToken(account.getEmail());

                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Login successful");
                responseBody.put("token", token);
                responseBody.put("user", account);
                account.setPassword(null);

                return ResponseEntity.ok(responseBody);
            } else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
            }
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Account not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

}
