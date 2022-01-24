package com.acmebank.service;

import com.acmebank.ui.request.TransferAmountRequest;
import com.acmebank.ui.response.AccountBalanceResponse;
import com.acmebank.ui.response.TransferAmountResponse;

import java.math.BigDecimal;

public interface AccountService {

    public AccountBalanceResponse getBalance(Integer accountId);
    public TransferAmountResponse transfer(Integer debitAccountId, Integer creditAccountId, BigDecimal amount);

}
