package com.acmebank;

import com.acmebank.domain.Account;
import com.acmebank.domain.Customer;
import com.acmebank.enums.Currency;
import com.acmebank.repository.AccountRepository;
import com.acmebank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Optional;

@SpringBootApplication
public class AccountManagerApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(AccountManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer customer = new Customer("Arvind","Verma","arvind.verma.hk@gmail.com");

		Optional<Account> optionalAccount = accountRepository.findById(12345678);

		if(optionalAccount.isEmpty()) {

			Account account1 = new Account(12345678, BigDecimal.valueOf(1000000), Currency.HKD);
			Account account2 = new Account(88888888, BigDecimal.valueOf(1000000), Currency.HKD);

			customer.addAccount(account1);
			customer.addAccount(account2);
			account1.setCustomer(customer);
			account2.setCustomer(customer);

			customerRepository.save(customer);
			accountRepository.save(account1);
			accountRepository.save(account2);
		}

	}
}
