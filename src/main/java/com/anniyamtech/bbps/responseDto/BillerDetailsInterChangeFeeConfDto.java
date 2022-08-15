package com.anniyamtech.bbps.responseDto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsInterChangeFeeConfDto {
	private String mti;
	private String paymentMode;
	private String paymentChannel;
	private String responseCode;
	
	 private List<String> fees;
	private Boolean defaultFee;
	private Date effctvFrom;
	private Date effctvTo;
	
}
