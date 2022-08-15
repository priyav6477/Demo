package com.anniyamtech.bbps.responseDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsBillerPlanResponseParams {
	private List<BillerDetailsPlanResponseParams>params;
	private List<BillerDetailsPlanResponseAmountOptions>amountOptions;
}
