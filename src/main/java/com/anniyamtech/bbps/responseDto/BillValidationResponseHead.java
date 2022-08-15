package com.anniyamtech.bbps.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillValidationResponseHead {
	private String refId;
	private String origInst;
	private String ts;
	private String ver;
}
