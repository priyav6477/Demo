package com.anniyamtech.bbps.requestDto;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerListReqDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Head head;
	private Search search;
	
}
