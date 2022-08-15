package com.anniyamtech.bbps.requestEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Embeddable
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaymentBillDetails {
	private String billerId;
	@ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "bill_payment_customer_params_request", joinColumns = @JoinColumn (name = "refId"))
	private List< BillPaymentCustomerParams> customerParams=new ArrayList< BillPaymentCustomerParams>();
}
