package com.anniyamtech.bbps.responseDto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaiseComplaintRespTxn {
	private String msgId;
	private String ts;
	private String xchangeId;
}
