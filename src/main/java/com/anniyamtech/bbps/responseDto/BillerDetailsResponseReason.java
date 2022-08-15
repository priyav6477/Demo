package com.anniyamtech.bbps.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsResponseReason {
	private String responseCode;
	private String responseReason;
	private String complianceRespCd;
	private String complianceReason;
}
