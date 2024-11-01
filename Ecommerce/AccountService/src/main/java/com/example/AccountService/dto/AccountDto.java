package com.example.AccountService.dto;

import com.example.AccountService.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
        return Account.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .shippingAddress(dto.getShippingAddress())
                .billingAddress(dto.getBillingAddress())
                .paymentMethod(dto.getPaymentMethod())
                .build();
    }
}
