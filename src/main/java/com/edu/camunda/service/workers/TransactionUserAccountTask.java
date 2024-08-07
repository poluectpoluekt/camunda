package com.edu.camunda.service.workers;

import com.edu.camunda.dao.TransactionDao;
import com.edu.camunda.dao.UserDao;
import com.edu.camunda.entity.User;
import com.edu.camunda.entity.operation.Topup;
import com.edu.hibernate.exception.user.UserNotFoundException;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@Component
public class TransactionUserAccountTask {

    private final UserDao userDao;
    private final TransactionDao transactionDao;

    @Transactional
    @JobWorker(type = "replenish", autoComplete = true)
    public void replenishUserAccount(ActivatedJob job) {

        User user = userDao.findUserByAccountNumber(job.getVariable("userAccountNumber").toString())
                .orElseThrow(()-> new UserNotFoundException(job.getVariable("userAccountNumber").toString()));

//        if (user == null) {
//            throw new ZeebeBpmnError("401", "User account not found",
//                    Map.of("userAccountNumber", job.getVariable("userAccountNumber")));
//        }

        user.setBalance(user.getBalance().add((BigDecimal) job.getVariable("amount")));

        Topup topup = new Topup();
        topup.setUserAccountNumber(job.getVariable("userAccountNumber").toString());
        topup.setAmount((BigDecimal) job.getVariable("amount"));
        topup.setOwner(user);

        userDao.updateUser(user);
        transactionDao.createTopup(topup);

    }



    @Transactional
    @JobWorker(type = "transfer", autoComplete = true)
    public void transferOperation(ActivatedJob job) {

        User user = userDao.findUserByAccountNumber(job.getVariable("userAccountNumber").toString())
                .orElseThrow(()-> new UserNotFoundException(job.getVariable("userAccountNumber").toString()));

    }
}
