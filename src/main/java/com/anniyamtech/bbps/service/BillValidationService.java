package com.anniyamtech.bbps.service;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.BillValidationRequest;

public interface BillValidationService {
	public BaseDTO billValidation(BillValidationRequest billValidationRequest) throws Exception;

}
