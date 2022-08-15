package com.anniyamtech.bbps.requestEntity;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

	@Data
	@EqualsAndHashCode(callSuper = false)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Embeddable

	public class BillPaymentCustomerTags {
	private String name;
	private String value;
	}

