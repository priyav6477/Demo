package com.anniyamtech.bbps.service;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.CheckComplaintStatusReqDto;

public interface CheckComplaintStatusService {
	public BaseDTO checkingComplaintStatus(CheckComplaintStatusReqDto checkComplaintReqDto);
	
}
