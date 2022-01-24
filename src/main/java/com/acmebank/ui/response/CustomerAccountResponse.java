package com.acmebank.ui.response;

import java.util.Objects;

public class CustomerAccountResponse {

    private final Integer customerId;
    private final AccountBalanceResponse account;

    public CustomerAccountResponse(Integer customerId, AccountBalanceResponse account) {
        this.customerId = customerId;
        this.account = account;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public AccountBalanceResponse getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAccountResponse)) return false;
        CustomerAccountResponse response = (CustomerAccountResponse) o;
        return Objects.equals(getCustomerId(), response.getCustomerId()) && Objects.equals(getAccount(), response.getAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getAccount());
    }
}
