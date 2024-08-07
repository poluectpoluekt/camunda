package com.edu.camunda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

/** Map<String, String> creditData
 *  firstName "",
 *  lastName "",
 *  numberPassport "",
 *  addressRegistration "",
 *  incomeInformation "",
 *  creditGoal "",
 *  creditPercentage "",
 *  creditRepaymentPeriod "",
 *  creditMonthlyPayment "",
 *  accountNumber ""
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class IssuanceOfCreditDto {

    private BigDecimal amountCredits;
    private String firstName;
    private String lastName;
    private String numberPassport;
    private String addressRegistration;
    private BigDecimal incomeInformation; // информация о доходе
    private String creditGoal;
    private double creditPercentage;
    private int creditRepaymentPeriod; // срок погашения, в пет-прокте в месяцах
    private BigDecimal creditMonthlyPayment;
    private String accountNumber;

}
