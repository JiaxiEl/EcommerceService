package com.example.AccountService.service;

import com.example.AccountService.dto.AccountDto;
import java.util.Optional;

public interface AccountService {
    Optional<AccountDto> getAccountById(Long id);
    Optional<AccountDto> getAccountByEmail(String email);
    Optional<AccountDto> updateAccountPassword(Long id, String password);
    void deleteAccount(Long id);
    AccountDto createAccount(AccountDto accountDto);
    boolean verifyPassword(String rawPassword, String encodedPassword);
    Optional<AccountDto> updateAccountBalance(Long accountId, Double amount);
}
