package com.edu.camunda.service.workers;

import com.edu.camunda.entity.ProcessIssuanceCreditData;
import com.edu.camunda.entity.UserCreditsContract;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class LendingStepsTasks {

    private Random rand = new Random();


    @JobWorker(type = "documentPreparation", autoComplete = true)
    public ProcessIssuanceCreditData documentPreparation(ActivatedJob job){
        ProcessIssuanceCreditData issuanceCreditData = job.getVariablesAsType(ProcessIssuanceCreditData.class);
        issuanceCreditData.setCreditMarginIndicator(0);

        //some code

        return issuanceCreditData;

    }

    @JobWorker(type = "requestCreditsReview", autoComplete = true)
    public ProcessIssuanceCreditData requestCreditsReview(ActivatedJob job){
        ProcessIssuanceCreditData issuanceCreditData = job.getVariablesAsType(ProcessIssuanceCreditData.class);

        int creditMetricsSumm = rand.nextInt(100);

        issuanceCreditData.setCreditMarginIndicator(creditMetricsSumm);

        return issuanceCreditData;
    }

    @JobWorker(type = "confirmCredits", autoComplete = true)
    public void confirmCredits(ActivatedJob job){
        //some code

    }

    @JobWorker(type = "rejectCredits", autoComplete = true)
    public void rejectCredits(ActivatedJob job){
        //some code
    }

    @Transactional
    @JobWorker(type = "conclusionOfContract", autoComplete = true)
    public void conclusionOfContract(ActivatedJob job){
        ProcessIssuanceCreditData issuanceCreditData = job.getVariablesAsType(ProcessIssuanceCreditData.class);
        UserCreditsContract contract = new UserCreditsContract();
        contract.setAmountCredits(issuanceCreditData.getAmountCredit());
        contract.setCreditRepaymentPeriod(issuanceCreditData.getCreditRepaymentPeriod());
        contract.setCreditPercentage(issuanceCreditData.getCreditPercentage());
        contract.setUserAccountOwner(issuanceCreditData.getAccountNumber());

        //метод сохранения контракта в БД
    }

    @Transactional
    @JobWorker(type = "transferFundsToAccount", autoComplete = true)
    public void transferFundsToAccount(ActivatedJob job){

        //увеличить баланс пользователя на сумму кредита.

    }
}
