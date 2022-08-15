package com.anniyamtech.bbps.service;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.BillerListReqDto;

public interface BillerListService {
	public BaseDTO billerList(BillerListReqDto billerListReqDto) throws Exception;
	/*
	 * public BillerListReqDto fetchByRefId(String refId); public
	 * List<BillerListReqDto> fetchAllBillerDetails(); public void
	 * updateBillerList(BillerListReqDto BillerListReqDto, String refId); public
	 * void deleteByRefId(String refId); public Optional<BillerListReq>
	 * getBillersById(String id);
	 */
}
