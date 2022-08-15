package com.anniyamtech.bbps.requestEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
public class BillFetchCustomerParams {
	@Column(name="customer_name")
	private String name;
	@Column(name="customer_value")
	private String value;
	
}
