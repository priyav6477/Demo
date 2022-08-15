package com.anniyamtech.bbps.responseDto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillValidationResponse {
	private BillValidationResponseHead head;
	private BillValidationResponseReason reason;
	private Set<BillValidationResponseAdditionalInfo> additionalInfoList;

}
