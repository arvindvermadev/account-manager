package com.acmebank.controllers;

import com.acmebank.service.CustomerService;
import com.acmebank.ui.request.TransferAmountRequest;
import com.acmebank.ui.response.CustomerAccountResponse;
import com.acmebank.ui.response.CustomerAccountsResponse;
import com.acmebank.ui.response.TransferAmountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("customers")
public class CustomerAccountController {

    private final CustomerService customerService;

    public CustomerAccountController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/{customerId}/accounts")
    public ResponseEntity<CustomerAccountsResponse> getCustomerAccounts(@PathVariable Integer customerId) {
        CustomerAccountsResponse response = customerService.getAccounts(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{customerId}/accounts/{accountId}")
    public ResponseEntity<CustomerAccountResponse> getCustomerAccount(@PathVariable Integer customerId, @PathVariable Integer accountId) {
        CustomerAccountResponse response = customerService.getAccount(customerId, accountId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/transferAmount")
    public ResponseEntity<TransferAmountResponse> transferAmount(@RequestBody @Valid TransferAmountRequest request) {
        TransferAmountResponse response = customerService.transferAmount(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
