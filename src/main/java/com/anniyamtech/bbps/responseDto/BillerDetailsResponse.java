package com.anniyamtech.bbps.responseDto;

import java.util.List;

import com.anniyamtech.bbps.requestDto.BillerDetailsBiller;
import com.anniyamtech.bbps.requestDto.BillerDetailsHead;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsResponse {
	private BillerDetailsResponseHead head  ;
	private BillerDetailsResponseReason reason;
	private BillerDetailsResponseBiller biller;
}
