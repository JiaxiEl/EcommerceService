package com.example.AccountService.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_accounts", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "account_balance")
    private Double accountBalance;

    @ElementCollection
    @CollectionTable(name = "order_history", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "order_id")
    private List<String> orderHistory; // Track orders associated with the account

    @ElementCollection
    @CollectionTable(name = "payment_history", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "transaction_id")
    private List<String> paymentHistory; // Track payment transactions

    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;
}
