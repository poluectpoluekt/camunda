package com.edu.camunda.controller;

import com.edu.camunda.dto.DebitDto;
import com.edu.camunda.dto.IssuanceOfCreditDto;
import com.edu.camunda.dto.TopupDto;
import com.edu.camunda.dto.TransferDto;
import com.edu.camunda.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/api/operations")
@RestController
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/debit")
    public void debit(@RequestBody DebitDto debitDto){
        operationService.replenishFunds(debitDto.getUserAccount(), debitDto.getDebitAmount());
    }

    @PostMapping("/topup")
    public void topup(@RequestBody TopupDto topupDto) {
        operationService.replenishFunds(topupDto.getToUserAccount(), topupDto.getAmount());
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferDto transferDto) {
        operationService.transferFunds(transferDto);
    }

    @PostMapping("/credits")
    public String credit(@RequestBody IssuanceOfCreditDto creditDto) {
        return operationService.startProcessIssuanceOfCredit(creditDto);
    }
}
