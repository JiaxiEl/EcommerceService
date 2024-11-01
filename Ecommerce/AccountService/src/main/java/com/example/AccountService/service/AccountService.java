package com.example.AccountService.service;

import com.example.AccountService.dto.AccountDto;
import com.example.AccountService.entity.Account;
import com.example.AccountService.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountDto.toEntity(accountDto);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account savedAccount = accountRepository.save(account);
        return AccountDto.fromEntity(savedAccount);
    }

    public Optional<AccountDto> getAccountById(Long id) {
        return accountRepository.findById(id).map(AccountDto::fromEntity);
    }

    public Optional<AccountDto> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).map(AccountDto::fromEntity);
    }

    public Optional<AccountDto> updateAccountPassword(Long id, String password) {
        return accountRepository.findById(id).map(account -> {
            if (password != null && !password.isEmpty()) {
                account.setPassword(password);
            }
            Account updatedAccount = accountRepository.save(account);
            return AccountDto.fromEntity(updatedAccount);
        });
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
