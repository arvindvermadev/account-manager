package com.acmebank.ui.response;

import java.math.BigDecimal;

public class TransferAmountResponse {


    private final Integer debitAccountId;
    private final BigDecimal debitAccountBalance;
    private final Integer creditAccountId;
    private final BigDecimal creditAccountBalance;

    public TransferAmountResponse(Integer debitAccountId, BigDecimal debitAccountBalance, Integer creditAccountId, BigDecimal creditAccountBalance) {
        this.debitAccountId = debitAccountId;
        this.debitAccountBalance = debitAccountBalance;
        this.creditAccountId = creditAccountId;
        this.creditAccountBalance = creditAccountBalance;
    }

    public Integer getDebitAccountId() {
        return debitAccountId;
    }

    public BigDecimal getDebitAccountBalance() {
        return debitAccountBalance;
    }

    public Integer getCreditAccountId() {
        return creditAccountId;
    }

    public BigDecimal getCreditAccountBalance() {
        return creditAccountBalance;
    }
}
