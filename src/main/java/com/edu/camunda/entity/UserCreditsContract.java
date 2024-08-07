package com.edu.camunda.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserCreditsContract {

    private long id;
    private BigDecimal amountCredits;
    private int creditRepaymentPeriod;
    private Double creditPercentage;
    private String userAccountOwner;
}
