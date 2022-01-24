package com.acmebank.domain;

import com.acmebank.enums.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Account {

    @Id
    private Integer id;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    Customer customer;

    private Account() {
    }

    public Account(Integer id, BigDecimal balance, Currency currency) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId()) && Objects.equals(getBalance(), account.getBalance()) && getCurrency() == account.getCurrency() && Objects.equals(getCustomer(), account.getCustomer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBalance(), getCurrency(), getCustomer());
    }
}
