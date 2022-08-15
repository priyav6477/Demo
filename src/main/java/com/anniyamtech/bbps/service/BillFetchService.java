package com.anniyamtech.bbps.service;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.BillFetchRequestDto;

public interface BillFetchService {
	public BaseDTO fetchBiller(BillFetchRequestDto billFetchRequestDto) throws Exception;

	
}
