package com.anniyamtech.bbps.requestDto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaymentRequestDto  {

	private BillPaymentHeadRequestDto head;
	private BillPaymentTxnRequestDto txn;

	 private List< BillPaymentAnalyticsRequestDto >analytics;
	private BillPaymentCustomerRequestDto customer;
	private BillPaymentAgentRequestDto agent;
	private BillPaymentBillDetailsRequestDto billDetails;
	private BillPaymentAmountRequestDto amount;
	private BillPayPaymentMethodRequestDto paymentMethod;
	private List<BillPaymentPaymentInformationRequestDto> paymentInformation;

	private String debitAccountNo;
}
