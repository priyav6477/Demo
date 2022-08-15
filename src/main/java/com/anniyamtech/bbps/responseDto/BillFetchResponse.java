package com.anniyamtech.bbps.responseDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillFetchResponse {
private BillFetchHeadResponse head;
private BillFetchReasonResponse reason;
private BillFetchTransactionResponse txn;
private BillFetchBillDetailsResponse billDetails;
private BillFetchBillerResponse billerResponse;
private List<BillFetchAdditionalInfo>additionalInfoList;
}
