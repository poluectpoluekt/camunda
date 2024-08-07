package com.edu.camunda.service;

import com.edu.camunda.dao.TransactionDao;
import com.edu.camunda.dto.IssuanceOfCreditDto;
import com.edu.camunda.dto.TopupDto;
import com.edu.camunda.dto.TransferDto;
import com.edu.camunda.entity.User;
import com.edu.camunda.entity.operation.Debit;
import com.edu.camunda.entity.operation.Topup;
import com.edu.camunda.entity.operation.Transfer;
import com.edu.camunda.exception.transaction.NegativeUserBalanceException;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OperationService {

    private final String PROCESS_REPLENISH = "REPLENISH_USER_ACCOUNT";

    private final String ISSUANCE_CREDIT = "ISSUANCE_CREDIT";

    private final ZeebeClient zeebeClient;

    private final UserService userService;

    private final TransactionDao repository;

    public long startProcessReplenishFunds(TopupDto topupDto){
        String uuid = UUID.randomUUID().toString();

        ProcessInstanceEvent instanceEvent = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_REPLENISH)
                .latestVersion()
                .variables(Map.of("userAccountNumber", topupDto.getToUserAccount(),
                        "amount", topupDto.getAmount(),
                        "messageId", uuid))
                .send().join();

        return instanceEvent.getProcessInstanceKey();

    }

    public String startProcessIssuanceOfCredit(IssuanceOfCreditDto creditDto){

        Map<String, String> creditData = new HashMap<>();
        creditData.put("messageId", UUID.randomUUID().toString());
        creditData.put("amountCredits", creditDto.getAmountCredits().toString());
        creditData.put("userAccountNumber", creditDto.getAccountNumber()); //сделано для упрощения
        creditData.put("incomeInformation", creditDto.getIncomeInformation().toString() );
        creditData.put("creditGoal", creditDto.getCreditGoal() );
        creditData.put("creditPercentage", Double.toString(creditDto.getCreditPercentage()) );
        creditData.put("creditRepaymentPeriod", Integer.toString(creditDto.getCreditRepaymentPeriod()) );
        creditData.put("creditMonthlyPayment", creditDto.getCreditMonthlyPayment().toString() );
        creditData.put("creditMarginIndicator", null);


        ProcessInstanceEvent instanceEvent = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(ISSUANCE_CREDIT)
                .latestVersion()
                .variables(creditData)
                .send().join();

        return "Number your request: " + instanceEvent.getProcessInstanceKey();
    }


    @Transactional
    public void replenishFunds(String userAccount, BigDecimal amount) {
        User user = userService.findUserByAccountNumber(userAccount);
        user.setBalance(user.getBalance().add(amount));

        Topup topup = new Topup();
        topup.setUserAccountNumber(userAccount);
        topup.setAmount(amount);
        topup.setOwner(user);

        userService.updateUser(user);
        repository.createTopup(topup);
    }


    @Transactional
    public void debitFunds(String userAccount, BigDecimal amount) {
        User user = userService.findUserByAccountNumber(userAccount);
        if (user.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeUserBalanceException();
        }
        user.setBalance(user.getBalance().subtract(amount));

        Debit debit = new Debit();
        debit.setUserAccountNumber(userAccount);
        debit.setAmount(amount);
        debit.setOwner(user);

        userService.updateUser(user);
        repository.createDebit(debit);
    }



    @Transactional
    public void transferFunds(TransferDto transferDto) {
        User userFrom = userService.findUserByAccountNumber(transferDto.getFromUserAccount());
        User userTo = userService.findUserByAccountNumber(transferDto.getToUserAccount());

        if (userFrom.getBalance().subtract(transferDto.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeUserBalanceException();
        }

        userFrom.setBalance(userFrom.getBalance().subtract(transferDto.getAmount()));
        userTo.setBalance(userTo.getBalance().add(transferDto.getAmount()));

        Transfer transfer = new Transfer();
        transfer.setFrom(transferDto.getFromUserAccount());
        transfer.setTarget(transferDto.getToUserAccount());
        transfer.setAmount(transferDto.getAmount());

        userService.updateUser(userFrom);
        userService.updateUser(userTo);
        repository.createTransfer(transfer);
    }
}
