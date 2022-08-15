package com.anniyamtech.bbps.requestDto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaymentTxnRequestDto {
private String msgId;
private String txnReferenceId;
private String ts;
private String type;
private List< BillPaymentRiskScoresRequestDto> riskScores = new ArrayList<BillPaymentRiskScoresRequestDto>();

}
