package com.anniyamtech.bbps.responseEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.anniyamtech.bbps.requestEntity.BillFetchCustomerParams;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Setter
@Getter
@Embeddable
public class BillFetchCustomerParamsRespEntity {
	@Column(name="customer_name")
	private String name;
	@Column(name="customer_value")
	private String value;
}
