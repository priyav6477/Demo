package com.anniyamtech.bbps.requestDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillValidationRequest {
private BillValidationHead head;
private String agentId;
private BillValidationBillDetailsReq billDetails;
}
