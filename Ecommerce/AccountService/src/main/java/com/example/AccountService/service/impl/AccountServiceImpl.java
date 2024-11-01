package com.example.AccountService.service.impl;

import com.example.AccountService.dto.AccountDto;
import com.example.AccountService.entity.Account;
import com.example.AccountService.repository.AccountRepository;
import com.example.AccountService.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountDto.toEntity(accountDto);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account savedAccount = accountRepository.save(account);
        return AccountDto.fromEntity(savedAccount);
    }

    @Override
    public Optional<AccountDto> getAccountById(Long id) {
        return accountRepository.findById(id).map(AccountDto::fromEntity);
    }

    @Override
    public Optional<AccountDto> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).map(AccountDto::fromEntity);
    }

    @Override
    public Optional<AccountDto> updateAccountPassword(Long id, String password) {
        return accountRepository.findById(id).map(account -> {
            if (password != null && !password.isEmpty()) {
                account.setPassword(passwordEncoder.encode(password));
            }
            Account updatedAccount = accountRepository.save(account);
            return AccountDto.fromEntity(updatedAccount);
        });
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
