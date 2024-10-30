package com.example.AccountService.service;

import com.example.AccountService.dto.AccountDto;
import com.example.AccountService.entity.Account;
import com.example.AccountService.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountDto.toEntity(accountDto);
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

    public boolean verifyPassword(String rawPassword, String storedPassword) {
        return rawPassword.equals(storedPassword);
    }
}
