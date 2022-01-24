package com.acmebank.service;

import com.acmebank.domain.Account;
import com.acmebank.domain.Customer;
import com.acmebank.enums.Currency;
import com.acmebank.exception.AccountNotFoundException;
import com.acmebank.exception.CustomerNotFoundException;
import com.acmebank.repository.CustomerRepository;
import com.acmebank.ui.request.TransferAmountRequest;
import com.acmebank.ui.response.AccountBalanceResponse;
import com.acmebank.ui.response.CustomerAccountResponse;
import com.acmebank.ui.response.CustomerAccountsResponse;
import com.acmebank.ui.response.TransferAmountResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountService accountService;

    Integer customerId = 12345;

    @Test
    void customerCanGetAllAccountsDetails() {

        doReturn(Optional.of(createCustomer())).when(customerRepository).findById(customerId);

        CustomerAccountsResponse response = service.getAccounts(customerId);

        List<AccountBalanceResponse> accounts = response.getAccounts();
        AccountBalanceResponse acc_1 = accounts.get(0);
        AccountBalanceResponse acc_2 = accounts.get(1);

        assertEquals(customerId, response.getCustomerId());
        assertEquals(new AccountBalanceResponse(12345, BigDecimal.valueOf(10000), Currency.HKD), acc_1);
        assertEquals(new AccountBalanceResponse(8888, BigDecimal.valueOf(10000), Currency.HKD), acc_2);

    }

    @Test
    void canThrowExceptionWhenCustomerNotFound() {

        doReturn(Optional.empty()).when(customerRepository).findById(customerId);

        assertThrows(CustomerNotFoundException.class,
                () -> service.getAccounts(customerId),
                "Customer does not exist");

    }

    @Test
    void canGetSpecificCustomerAccountByAccountId() {

        doReturn(Optional.of(createCustomer())).when(customerRepository).findById(customerId);


        CustomerAccountResponse response = service.getAccount(customerId, 8888);
        AccountBalanceResponse account = response.getAccount();

        assertEquals(customerId, response.getCustomerId());
        assertEquals(new AccountBalanceResponse(8888, BigDecimal.valueOf(10000), Currency.HKD), account);

    }

    @Test
    void customerCanPerformTransferMoney() {

        Integer debitAccountId = 12345;
        Integer creditAccountId = 8888;
        BigDecimal amount = BigDecimal.valueOf(500);

        TransferAmountRequest request = new TransferAmountRequest(customerId,
                debitAccountId, creditAccountId, amount);

        doReturn(Optional.of(createCustomer())).when(customerRepository).findById(customerId);

        service.transferAmount(request);

        verify(accountService, times(1)).transfer(debitAccountId, creditAccountId, amount);

    }


    private Customer createCustomer() {

        Account acc_1 = new Account(12345, BigDecimal.valueOf(10000), Currency.HKD);
        Account acc_2 = new Account(8888, BigDecimal.valueOf(10000), Currency.HKD);

        Customer customer = new Customer("Arvind", "Verma", "arvind.verma.hk@gmail.com");
        customer.setId(customerId);

        customer.addAccount(acc_1);
        customer.addAccount(acc_2);

        return customer;

    }


}