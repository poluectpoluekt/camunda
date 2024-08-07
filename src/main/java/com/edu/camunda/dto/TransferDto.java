package com.edu.camunda.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TransferDto {

    private String fromUserAccount;
    private String toUserAccount;
    private BigDecimal amount;
}
