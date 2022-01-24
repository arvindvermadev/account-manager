package com.acmebank.service;

import com.acmebank.domain.Account;
import com.acmebank.exception.AccountNotFoundException;
import com.acmebank.exception.InsufficientFundsException;
import com.acmebank.repository.AccountRepository;
import com.acmebank.ui.response.AccountBalanceResponse;
import com.acmebank.ui.response.TransferAmountResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountBalanceResponse getBalance(Integer accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException("account can not be found for accountId : " + accountId);
        }

        Account account = optionalAccount.get();
        return new AccountBalanceResponse(
                account.getId(), account.getBalance(), account.getCurrency());

    }

    @Override
    @Transactional
    public TransferAmountResponse transfer(Integer debitAccountId, Integer creditAccountId, BigDecimal amount) {

        Optional<Account> optionDebitAccount = accountRepository.findById(debitAccountId);

        if (optionDebitAccount.isEmpty()) {
            throw new AccountNotFoundException("Debit account can not be found with debitAccountId " + debitAccountId);
        }
        Optional<Account> optionalCreditAccount = accountRepository.findById(creditAccountId);

        if (optionalCreditAccount.isEmpty()) {
            throw new AccountNotFoundException("Credit account can not be found with creditAccountId " + creditAccountId);
        }

        Account debitAccount = optionDebitAccount.get();
        Account creditAccount = optionalCreditAccount.get();

        if (debitAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Not enough funds to transfer, current balance is " + debitAccount.getBalance() +
                    " and requested transfer amount is " + amount);
        }

        updateBalance(amount, debitAccount, creditAccount);

        Account savedDebit = accountRepository.save(debitAccount);
        Account savedCredit = accountRepository.save(creditAccount);

        return new TransferAmountResponse(savedDebit.getId(), savedDebit.getBalance(),
                savedCredit.getId(), savedCredit.getBalance());

    }

    protected void updateBalance(BigDecimal amount, Account debitAccount, Account creditAccount) {

        BigDecimal newDebitAccountBalance = debitAccount.getBalance().subtract(amount);
        debitAccount.setBalance(newDebitAccountBalance);
        BigDecimal newCreditAccountBalance = creditAccount.getBalance().add(amount);
        creditAccount.setBalance(newCreditAccountBalance);

    }
}
