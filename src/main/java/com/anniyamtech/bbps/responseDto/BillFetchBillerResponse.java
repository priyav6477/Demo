package com.anniyamtech.bbps.responseDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillFetchBillerResponse {
private String customerName;
private String amount;
private String dueDate;
private String billDate;
private String billNumber;
private String billPeriod;
private String custConvFee;
private String custConvDesc;
private List<BillerResponseTags>tags;
}
