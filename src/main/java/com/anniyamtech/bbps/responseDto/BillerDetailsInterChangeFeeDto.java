package com.anniyamtech.bbps.responseDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsInterChangeFeeDto {
	private String feeCode;
	private String feeDesc;
	private String feeDirection;
	
	private List<BillerDetailsInterChangeFeeDetails> interchangeFeeDetails;
}
