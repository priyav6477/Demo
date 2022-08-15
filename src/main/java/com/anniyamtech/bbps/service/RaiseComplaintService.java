package com.anniyamtech.bbps.service;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.RaiseComplaintReqDto;

public interface RaiseComplaintService {
	public BaseDTO raiseComplaintAgainstTransaction(RaiseComplaintReqDto reqTxnDto) throws Exception;

	public BaseDTO raiseComplaintAgainstAgent(RaiseComplaintReqDto requestdto) throws Exception;

	public BaseDTO raiseComplaintAgainstBiller(RaiseComplaintReqDto reqBillerDto) throws Exception;

}
