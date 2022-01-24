package com.acmebank.ui.request;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferAmountRequest {

    @NotNull(message = "Customer Id cannot be null")
    private final Integer customerId;
    private final Integer debitAccountId;
    @NotNull(message = "Credit Account Id cannot be null")
    private final Integer creditAccountId;
    @NotNull
    @DecimalMin(value = "0.0")
    private final BigDecimal amount;


    public TransferAmountRequest(Integer customerId, Integer debitAccountId, Integer creditAccountId, BigDecimal amount) {
        this.customerId = customerId;
        this.debitAccountId = debitAccountId;
        this.creditAccountId = creditAccountId;
        this.amount = amount;
    }

    public Integer getDebitAccountId() {
        return debitAccountId;
    }

    public Integer getCreditAccountId() {
        return creditAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getCustomerId() {
        return customerId;
    }
}
