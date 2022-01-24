package com.acmebank.ui.response;

import java.util.List;

public class CustomerAccountsResponse {

    private final Integer customerId;
    private final List<AccountBalanceResponse> accounts;

    public CustomerAccountsResponse(Integer customerId, List<AccountBalanceResponse> accounts) {
        this.customerId = customerId;
        this.accounts = accounts;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public List<AccountBalanceResponse> getAccounts() {
        return accounts;
    }


}
