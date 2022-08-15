package com.anniyamtech.bbps.responseDto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsInterChangeFeeDetails {
	private long tranAmtRangeMax;
	private long tranAmtRangeMin;
	private long percentFee;
	private long flatFee;
	private Date effctvFrom;
	private Date effctvTo;

}
