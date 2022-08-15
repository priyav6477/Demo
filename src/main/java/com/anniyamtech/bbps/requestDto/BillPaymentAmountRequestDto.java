package com.anniyamtech.bbps.requestDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaymentAmountRequestDto {
private String amount;
private String custConvFee;
private String couCustConvFee;
private String currency;
private String splitPayAmount;
private String tags;
}
