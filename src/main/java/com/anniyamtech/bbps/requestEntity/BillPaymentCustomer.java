package com.anniyamtech.bbps.requestEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class  BillPaymentCustomer {
	@Column ( length =10)
	private String mobile;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_payment_customer_tags", joinColumns = @JoinColumn (name = "refId"))
	private List<BillPaymentCustomerTags> tags=new ArrayList<BillPaymentCustomerTags>();
	}

