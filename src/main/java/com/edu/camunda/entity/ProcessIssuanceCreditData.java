package com.edu.camunda.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessIssuanceCreditData {

            private BigDecimal incomeInformation; // информация о доходе
            private String creditGoal;
            private double creditPercentage;
            private int creditRepaymentPeriod; // срок погашения, в пет-прокте в месяцах
            private BigDecimal creditMonthlyPayment;
            private String accountNumber;
            private BigDecimal amountCredit;
            private int creditMarginIndicator;

}
