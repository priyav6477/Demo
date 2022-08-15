package com.anniyamtech.bbps.responseDto;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaymentRespBillerResponseDto {
	private String customerName;
	private String amount;
	private Date dueDate;
	private Date billDate;
	private String billNumber;
	private String billPeriod;
	private Set <BillPaymentRespBillerResponseTagsDto> tags ;
}
