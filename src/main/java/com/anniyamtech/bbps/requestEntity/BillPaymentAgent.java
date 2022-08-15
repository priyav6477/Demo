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

@Data
@Embeddable
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillPaymentAgent {
	private String id;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_payment_device_request", joinColumns = @JoinColumn(name = "refId"))
	private List<BillPaymentDevice> Device=new ArrayList<BillPaymentDevice>();
}
