package com.anniyamtech.bbps.service;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.BillPaymentRequestDto;

public interface BankBranchBillPaymentService {

public	BaseDTO billPayment(BillPaymentRequestDto bankBranchBillPaymentRequestDto) throws Exception;

}
