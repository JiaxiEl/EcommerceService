package com.example.AccountService.service;

import com.example.AccountService.dto.AccountDto;

import java.util.Optional;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    Optional<AccountDto> getAccountById(Long id);

    Optional<AccountDto> getAccountByEmail(String email);

    Optional<AccountDto> updateAccountPassword(Long id, String password);

    void deleteAccount(Long id);

    boolean verifyPassword(String rawPassword, String hashedPassword);
}
