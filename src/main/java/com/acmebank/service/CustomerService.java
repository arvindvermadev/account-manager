package com.acmebank.service;

import com.acmebank.ui.request.CustomerBalanceRequest;
import com.acmebank.ui.request.TransferAmountRequest;
import com.acmebank.ui.response.CustomerAccountResponse;
import com.acmebank.ui.response.CustomerAccountsResponse;
import com.acmebank.ui.response.TransferAmountResponse;

import java.math.BigDecimal;

public interface CustomerService {

    CustomerAccountsResponse getAccounts(Integer customerId);
    CustomerAccountResponse getAccount(Integer customerId, Integer accountId);
    TransferAmountResponse transferAmount(TransferAmountRequest request);
}
