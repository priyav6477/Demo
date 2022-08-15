package com.anniyamtech.bbps.service.Impl;

import java.net.ConnectException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anniyamtech.bbps.BbpsApplication;
import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.exception.ServerNotAccessableExcption;
import com.anniyamtech.bbps.repository.BillerListRepository;
import com.anniyamtech.bbps.repository.BillerListResponseRepository;
import com.anniyamtech.bbps.repository.BillersRepository;
import com.anniyamtech.bbps.requestDto.BankReqDto;
import com.anniyamtech.bbps.requestDto.BillerListReqDto;
import com.anniyamtech.bbps.requestDto.Head;
import com.anniyamtech.bbps.requestDto.Search;
import com.anniyamtech.bbps.requestEntity.BillerListReq;
import com.anniyamtech.bbps.responseDto.BankResponseDto;
import com.anniyamtech.bbps.responseDto.BillerListResponseDto;
import com.anniyamtech.bbps.responseDto.CheckComplaintStatusResponseDto;
import com.anniyamtech.bbps.responseEntity.BillerListResponse;
import com.anniyamtech.bbps.service.BillerListService;
import com.anniyamtech.bbps.utils.BbpsAes;
import com.anniyamtech.bbps.utils.JulianDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@Log4j2

@Service
public class BillerListServiceImp implements BillerListService {
	@Value("${kotakbank.web.billerList}")
	String billerListUrl;

	@Value("${agentInstitutionId}")
	String agentId;

	@Autowired
	BbpsAes bbps;

	@Autowired
	BillerListRepository billerListRepository;

	@Autowired
	BillerListResponseRepository billerListResponseRepo;

	@Autowired
	BillersRepository billersRepo;

	BankReqDto bankReq = new BankReqDto();
	Head head = new Head();
	Search search = new Search();
	Gson gson = new Gson();
	BaseDTO responseDTO = new BaseDTO();

	@Override
	public BaseDTO billerList(BillerListReqDto billerListReqDto) throws Exception {

		try {
			log.info("<-------- BillerList.service >> Started-------------->");

			String refId = JulianDateConverter.generationLogic();
			billerListReqDto.getHead().setRefId(refId);

			String date = JulianDateConverter.dateFormatter();

			billerListReqDto.getHead().setTs(date);
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			BillerListReq billerListReq = new BillerListReq();
			billerListReq.setVer(billerListReqDto.getHead().getVer());
			billerListReq.setRefId(refId);
			billerListReq.setOrigInst(billerListReqDto.getHead().getOrigInst());
			billerListReq.setTs(date);
			billerListReq.setCategory(billerListReqDto.getSearch().getCategory());
			billerListReq.setLastUpdated(billerListReqDto.getSearch().getLastUpdated());
			billerListRepository.save(billerListReq);
			String requestBody = gson.toJson(billerListReqDto);
			log.info("<====Request to Json:"+requestBody);

			String EncryptedData = bbps.encryption(requestBody);
			log.info("<====Encrypted request:"+EncryptedData);
			bankReq.setRequestMsg(EncryptedData);
			bankReq.setAgentInstitutionId(agentId);
			String bankReqJson = gson.toJson(bankReq);
			log.info("<====Bank Request to Json:"+bankReqJson);
			BbpsApplication.disableSSL();

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> entity = new HttpEntity<String>(bankReqJson, headers);
			log.info("<====Final Request:"+entity);
			RestTemplate restTemplate = new RestTemplate();

			String response = restTemplate.postForObject(billerListUrl, entity, String.class);
			log.info("<====Json response:"+response);

			BankResponseDto biller = gson.fromJson(response, BankResponseDto.class);

			log.info("<====Converted Json Response:"+biller);
			String DecryptedData = bbps.decryption(biller.getResponseMsg());
			log.info("<====Decrypted Response:"+DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:"+json);
			Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

			BillerListResponseDto newresponse = gson.fromJson(json, BillerListResponseDto.class);
			log.info("<====Converted Json Response:"+newresponse);
			BillerListResponse billerList = new BillerListResponse();

			if (newresponse.getReason().getResponseCode().equals("000")) {

				billerList.setRefId(newresponse.getHead().getRefId());
				billerList.setOrigInst(newresponse.getHead().getOrigInst());
				billerList.setTs(date);
				billerList.setVer(newresponse.getHead().getVer());
				billerList.setResponseCode(newresponse.getReason().getResponseCode());
				billerList.setResponseReason(newresponse.getReason().getResponseReason());

				billerListResponseRepo.save(billerList);

				/*
				 * List<BillersDto> billerDto = new ArrayList<BillersDto>();
				 * 
				 * for (BillersDto billers : newresponse.getBillers()) { billerDto.add(billers);
				 * BillersResponse billersList = new BillersResponse();
				 * billersList.setBillerId(billers.getBillerId());
				 * billersList.setBillerName(billers.getBillerName());
				 * billersList.setBillerAliasName(billers.getBillerAliasName());
				 * billersList.setBillerCategoryName(billers.getBillerCategoryName());
				 * billersList.setStatus(billers.getStatus());
				 * billersList.setLastUpdated(billers.getLastUpdated());
				 * billersList.setBiller(billerList);
				 * 
				 * billersRepo.save(billersList);
				 * 
				 * }
				 */
				responseDTO.setMessage("Biller List Fetched Successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(newresponse);
				log.info("<-------- BillerList.service >> Ended-------------->");

			}

			else {

				billerList.setRefId(newresponse.getHead().getRefId());
				billerList.setOrigInst(newresponse.getHead().getOrigInst());
				billerList.setTs(date);
				billerList.setVer(newresponse.getHead().getVer());
				billerList.setResponseCode(newresponse.getReason().getResponseCode());
				billerList.setResponseReason(newresponse.getReason().getResponseReason());
				billerList.setComplianceRespCd(newresponse.getReason().getComplianceRespCd());
				billerList.setComplianceRespCd(newresponse.getReason().getComplianceReason());

				billerListResponseRepo.save(billerList);
				responseDTO.setMessage("Unable to fetch Biller List");
				responseDTO.setStatusCode(201);
				responseDTO.setData(newresponse);
				log.info("<-------- BillerList.service >> Getting Failure response-------------->");

			}

		} catch (Exception e) {

			log.error(":: exception ::", e);
			log.info(":: ConnectException ::", e);
			try {

				throw new ConnectException();

			} catch (ConnectException ce) {

				log.info(":: ConnectException ::", ce);
				responseDTO.setMessage(" Bank Server Not Working");
				responseDTO.setStatusCode(500);
				responseDTO.setData(CheckComplaintStatusResponseDto.class);
				throw new ServerNotAccessableExcption();

			}
		}

		return responseDTO;
	}

}