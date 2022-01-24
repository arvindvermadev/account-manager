package com.acmebank.ui.response;

import com.acmebank.enums.Currency;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountBalanceResponse {

    private final Integer accountId;
    private final BigDecimal balance;
    private final Currency currency;

    public AccountBalanceResponse(Integer accountId, BigDecimal balance, Currency currency) {
        this.accountId = accountId;
        this.balance = balance;
        this.currency = currency;
    }


    public Integer getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBalanceResponse)) return false;
        AccountBalanceResponse response = (AccountBalanceResponse) o;
        return Objects.equals(getAccountId(), response.getAccountId()) && Objects.equals(getBalance(), response.getBalance()) && getCurrency() == response.getCurrency();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getBalance(), getCurrency());
    }
}
