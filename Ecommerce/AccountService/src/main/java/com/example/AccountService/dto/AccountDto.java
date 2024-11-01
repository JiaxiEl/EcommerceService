package com.example.AccountService.dto;

import com.example.AccountService.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;

    public static AccountDto fromEntity(Account account) {
        return new AccountDto(
                account.getId(),
                account.getEmail(),
                account.getUsername(),
                account.getPassword(),
                account.getShippingAddress(),
                account.getBillingAddress(),
                account.getPaymentMethod()
        );
    }

    public static Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setEmail(dto.getEmail());
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        account.setShippingAddress(dto.getShippingAddress());
        account.setBillingAddress(dto.getBillingAddress());
        account.setPaymentMethod(dto.getPaymentMethod());
        return account;
    }
}
