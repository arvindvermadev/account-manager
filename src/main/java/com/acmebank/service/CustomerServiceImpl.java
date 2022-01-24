package com.acmebank.service;

import com.acmebank.domain.Account;
import com.acmebank.domain.Customer;
import com.acmebank.exception.AccountNotFoundException;
import com.acmebank.exception.CustomerNotFoundException;
import com.acmebank.repository.CustomerRepository;
import com.acmebank.ui.request.TransferAmountRequest;
import com.acmebank.ui.response.AccountBalanceResponse;
import com.acmebank.ui.response.CustomerAccountResponse;
import com.acmebank.ui.response.CustomerAccountsResponse;
import com.acmebank.ui.response.TransferAmountResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }

    @Override
    public CustomerAccountsResponse getAccounts(Integer customerId) {

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("customer can not be found for customerId " + customerId);
        }

        Customer customer = optionalCustomer.get();
        List<Account> accounts = customer.getAccounts();

        List<AccountBalanceResponse> accountsResponse = accounts.stream()
                .map(account -> new AccountBalanceResponse(account.getId(), account.getBalance(), account.getCurrency()))
                .collect(Collectors.toList());


        return new CustomerAccountsResponse(customer.getId(), accountsResponse);
    }

    @Override
    public CustomerAccountResponse getAccount(Integer customerId, Integer accountId) {

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("customer can not be found for customerId " + customerId);
        }

        Customer customer = optionalCustomer.get();
        List<Account> accounts = customer.getAccounts();


        Optional<AccountBalanceResponse> optionalAccResponse = accounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .map(account -> new AccountBalanceResponse(account.getId(), account.getBalance(), account.getCurrency()))
                .findFirst();

        if (optionalAccResponse.isEmpty()) {
            throw new AccountNotFoundException("account is not found for customer with customerId " + customer
                    + " and accountId " + accountId);
        }
        AccountBalanceResponse response = optionalAccResponse.get();
        return new CustomerAccountResponse(customer.getId(), response);
    }

    @Override
    public TransferAmountResponse transferAmount(TransferAmountRequest request) {

        Integer customerId = request.getCustomerId();
        Integer debitAccountId = request.getDebitAccountId();
        Integer creditAccountId = request.getCreditAccountId();
        BigDecimal amount = request.getAmount();

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("customer can not be found for customerId " + customerId + " in the request");
        }

        Customer customer = optionalCustomer.get();
        List<Account> accounts = customer.getAccounts();

        Optional<Account> optionalAccount = accounts.stream()
                .filter(account -> account.getId().equals(debitAccountId))
                .findFirst();

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException("Debit account is not belong to customerId " + customerId + " in the request");
        }

        return accountService.transfer(debitAccountId, creditAccountId, amount);

    }
}
