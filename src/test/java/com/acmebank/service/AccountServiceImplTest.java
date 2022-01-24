package com.acmebank.service;

import com.acmebank.domain.Account;
import com.acmebank.enums.Currency;
import com.acmebank.exception.AccountNotFoundException;
import com.acmebank.exception.InsufficientFundsException;
import com.acmebank.repository.AccountRepository;
import com.acmebank.ui.response.AccountBalanceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl service;
    @Mock
    private AccountRepository accountRepository;


    Integer accountId = 12345;
    BigDecimal balance = BigDecimal.valueOf(10000);
    Account account = new Account(accountId, balance, Currency.HKD);

    @Test
    void canGetAccountBalance() {
        Integer accountId = 12345;
        doReturn(Optional.of(account)).when(accountRepository).findById(accountId);

        AccountBalanceResponse response = service.getBalance(accountId);

        assertEquals(Currency.HKD, response.getCurrency());
        assertEquals(accountId, response.getAccountId());
        assertEquals(balance, response.getBalance());

    }

    @Test
    void canThrowAccountNotFoundException() {

        doReturn(Optional.empty()).when(accountRepository).findById(accountId);

        assertThrows(AccountNotFoundException.class,
                () -> service.getBalance(accountId),
                "Account does not exist");

    }

    @Test
    void canTransferFundsBetweenAccounts() {

        BigDecimal debitAccountBalance = BigDecimal.valueOf(10000);
        BigDecimal creditAccountBalance = BigDecimal.valueOf(10000);
        BigDecimal amount = BigDecimal.valueOf(500);

        Account debitAccount = new Account(12345678, debitAccountBalance, Currency.HKD);
        Account creditAccount = new Account(88888888, creditAccountBalance, Currency.HKD);

        service.updateBalance(amount, debitAccount, creditAccount);

        assertEquals(creditAccountBalance.add(amount), creditAccount.getBalance());
        assertEquals(debitAccountBalance.subtract(amount), debitAccount.getBalance());

    }

    @Test
    void canUpdateFundsInDBWithTransfer() {

        Integer debitAccountId = 12345678;
        Integer creditAccountId = 888888888;

        Account debitAccount = new Account(debitAccountId, BigDecimal.valueOf(10000), Currency.HKD);
        Account creditAccount = new Account(creditAccountId, BigDecimal.valueOf(10000), Currency.HKD);
        BigDecimal amount = BigDecimal.valueOf(500);

        doReturn(Optional.of(debitAccount)).when(accountRepository).findById(debitAccountId);
        doReturn(Optional.of(creditAccount)).when(accountRepository).findById(creditAccountId);

        doReturn(debitAccount).when(accountRepository).save(debitAccount);
        doReturn(creditAccount).when(accountRepository).save(creditAccount);

        service.transfer(debitAccountId, creditAccountId, amount);

        verify(accountRepository, timeout(1)).save(debitAccount);
        verify(accountRepository, timeout(1)).save(creditAccount);

    }

    @Test
    void canThrowInsufficientFundsException() {
        Integer debitAccountId = 12345678;
        Integer creditAccountId = 888888888;

        Account debitAccount = new Account(debitAccountId, BigDecimal.valueOf(10000), Currency.HKD);
        Account creditAccount = new Account(creditAccountId, BigDecimal.valueOf(10000), Currency.HKD);

        BigDecimal amount = BigDecimal.valueOf(20000);

        doReturn(Optional.of(debitAccount)).when(accountRepository).findById(debitAccountId);
        doReturn(Optional.of(creditAccount)).when(accountRepository).findById(creditAccountId);

        assertThrows(InsufficientFundsException.class,
                () -> service.transfer(debitAccountId, creditAccountId, amount),
                "Not enough funds to do transfer");
    }
}