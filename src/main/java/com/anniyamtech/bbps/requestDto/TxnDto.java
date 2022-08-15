package com.anniyamtech.bbps.requestDto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TxnDto {
    
	private	String msgId;
	private	String ts;
	private	String xchangeId;
}
