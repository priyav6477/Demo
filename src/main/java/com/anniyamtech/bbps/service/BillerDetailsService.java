package com.anniyamtech.bbps.service;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.BillerDetailsRequestDto;

public interface BillerDetailsService {
	public BaseDTO billerDetails(BillerDetailsRequestDto billerDetailsRequestDTO) throws Exception;

	
}
