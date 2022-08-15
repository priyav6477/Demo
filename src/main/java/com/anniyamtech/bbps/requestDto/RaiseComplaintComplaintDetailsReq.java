package com.anniyamtech.bbps.requestDto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaiseComplaintComplaintDetailsReq {
private String complaintType;
private String participationType;
private String agentId;
private String billerId;

private String servReason;
private String description;
}
