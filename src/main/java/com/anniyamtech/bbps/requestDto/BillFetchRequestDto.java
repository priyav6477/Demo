package com.anniyamtech.bbps.requestDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillFetchRequestDto  {
	private static final long serialVersionUID=1L;
	private BillFetchHeadRequest head  ;
	private BillFetchRequestTransaction txn;
	private List<BillFetchAnalyticsTagRequest> analytics;
	private BillFetchCustomerRequest customer;
	private BillFetchRequestAgent agent;
	private BillFetchBillDetailsRequest billDetails; 

}
