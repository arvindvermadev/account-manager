package com.acmebank.ui.request;

import javax.validation.constraints.NotNull;

public class CustomerBalanceRequest {

    @NotNull(message = "customer Id cannot be null")
    private Integer customerId;
    @NotNull(message = "account Id cannot be null")
    private Integer accountId;

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getAccountId() {
        return accountId;
    }
}
