package com.anniyamtech.bbps.requestDto;

import java.util.Date;
import java.util.Set;

import com.anniyamtech.bbps.requestEntity.BillFetchRiskScoresTag;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillFetchRequestCustomerTag {
private String name;
private String value;
}
