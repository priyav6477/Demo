package com.anniyamtech.bbps.service.Impl;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anniyamtech.bbps.BbpsApplication;
import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.exception.ServerNotAccessableExcption;

import com.anniyamtech.bbps.repository.BillerDetailsRepository;
import com.anniyamtech.bbps.repository.BillerDetailsRespRepository;
import com.anniyamtech.bbps.requestDto.BankReqDto;
import com.anniyamtech.bbps.requestDto.BillerDetailsBiller;
import com.anniyamtech.bbps.requestDto.BillerDetailsHead;
import com.anniyamtech.bbps.requestDto.BillerDetailsRequestDto;
import com.anniyamtech.bbps.requestEntity.BillerDetailsEntity;
import com.anniyamtech.bbps.responseDto.BankResponseDto;
import com.anniyamtech.bbps.responseDto.BillerDetailsAmountOptions;
import com.anniyamtech.bbps.responseDto.BillerDetailsBillerAdditionalInfoDto;
import com.anniyamtech.bbps.responseDto.BillerDetailsBillerCustomerParams;
import com.anniyamtech.bbps.responseDto.BillerDetailsBillerPaymentChannelsDto;
import com.anniyamtech.bbps.responseDto.BillerDetailsBillerPaymentModesDto;
import com.anniyamtech.bbps.responseDto.BillerDetailsInterChangeFeeConfDto;
import com.anniyamtech.bbps.responseDto.BillerDetailsInterChangeFeeDetails;
import com.anniyamtech.bbps.responseDto.BillerDetailsInterChangeFeeDto;
import com.anniyamtech.bbps.responseDto.BillerDetailsPlanResponseAmountOptions;
import com.anniyamtech.bbps.responseDto.BillerDetailsResponse;

import com.anniyamtech.bbps.responseEntity.BillerDetailsRespEntity;
import com.anniyamtech.bbps.service.BillerDetailsService;
import com.anniyamtech.bbps.utils.BbpsAes;
import com.anniyamtech.bbps.utils.JulianDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BillerDetailsServiceImpl implements BillerDetailsService {
	@Value("${kotakbank.web.billerDetails}")
	String billerDetailsUrl;

	@Value("${agentInstitutionId}")
	String agentId;

	@Autowired
	BillerDetailsRepository billerDetailsRepository;
	@Autowired
	BbpsAes bbps;
	@Autowired
	BillerDetailsRespRepository billerDetailsRespRepository;

	BillerDetailsRespEntity detailsRespEntity = new BillerDetailsRespEntity();

	BankReqDto bankReq = new BankReqDto();
	BillerDetailsHead head = new BillerDetailsHead();
	BillerDetailsBiller biller = new BillerDetailsBiller();

	Gson gson = new Gson();
	BaseDTO responseDTO = new BaseDTO();

	@Override
	public BaseDTO billerDetails(BillerDetailsRequestDto billerDetailsRequestDTO) throws Exception {
		// TODO Auto-generated method stub
		try {
			log.info("<-------- BillerDetails.save >> Started-------------->");
			String refId = JulianDateConverter.generationLogic();
			String date = JulianDateConverter.dateFormatter();
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			billerDetailsRequestDTO.getHead().setTs(date);
			BillerDetailsEntity billerDetails = new BillerDetailsEntity();
			billerDetailsRequestDTO.getHead().setRefId(refId);
			billerDetails.setVer(billerDetailsRequestDTO.getHead().getVer());
			billerDetails.setTs(billerDetailsRequestDTO.getHead().getTs());
			billerDetails.setOrigInst(billerDetailsRequestDTO.getHead().getOrigInst());
			billerDetails.setRefId(refId);
			billerDetails.setBillerId(billerDetailsRequestDTO.getBiller().getBillerId());
			billerDetailsRepository.save(billerDetails);
			String requestBody = gson.toJson(billerDetailsRequestDTO);
			log.info("<==========================" + requestBody + "========================>");
			String EncryptedData = bbps.encryption(requestBody);
			bankReq.setRequestMsg(EncryptedData);
			bankReq.setAgentInstitutionId(agentId);
			log.info("<----------======================" + EncryptedData + "====================------------->");
			String bankReqJson = gson.toJson(bankReq);
			log.info("<==============" + bankReqJson + "==============>");
			BbpsApplication.disableSSL();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> entity = new HttpEntity<String>(bankReqJson, headers);
			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.postForObject(billerDetailsUrl, entity, String.class);
			log.info("/////////////////=========" + response + "==============////////////////");

			BankResponseDto biller = gson.fromJson(response, BankResponseDto.class);
			log.info("Bank Response : " + biller);
			log.info("!!!!!!!!!!!!!!!!" + biller.getResponseMsg() + "####################");

			String DecryptedData = bbps.decryption(biller.getResponseMsg());
			log.info("<====Decrypted data:" + DecryptedData);
			String json = DecryptedData;
			log.info("<====Decrypted json data:" + json);

			Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
			BillerDetailsResponse billerResponseDto = gson.fromJson(json, BillerDetailsResponse.class);
			log.info("<====Converted json data:" + billerResponseDto);

			if (billerResponseDto.getReason().getResponseCode().equals("000")) {
				detailsRespEntity.setRefId(billerResponseDto.getHead().getRefId());
				detailsRespEntity.setVer(billerResponseDto.getHead().getVer());
				detailsRespEntity.setTs(billerResponseDto.getHead().getTs());
				detailsRespEntity.setOrigInst(billerResponseDto.getHead().getOrigInst());
				detailsRespEntity.setResponseCode(billerResponseDto.getReason().getResponseCode());
				detailsRespEntity.setResponseReason(billerResponseDto.getReason().getResponseReason());
				detailsRespEntity.setComplianceRespCd(billerResponseDto.getReason().getComplianceRespCd());
				detailsRespEntity.setComplianceReason(billerResponseDto.getReason().getComplianceReason());

				billerDetailsRespRepository.save(detailsRespEntity);

				responseDTO.setMessage("Biller Details Fetched Successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(billerResponseDto);
				log.info("<-------- BillerDetails.save >> Ended-------------->");
			}

			else {
				detailsRespEntity.setRefId(billerResponseDto.getHead().getRefId());
				detailsRespEntity.setVer(billerResponseDto.getHead().getVer());
				detailsRespEntity.setTs(billerResponseDto.getHead().getTs());
				detailsRespEntity.setOrigInst(billerResponseDto.getHead().getOrigInst());
				detailsRespEntity.setResponseCode(billerResponseDto.getReason().getResponseCode());
				detailsRespEntity.setResponseReason(billerResponseDto.getReason().getResponseReason());
				detailsRespEntity.setComplianceRespCd(billerResponseDto.getReason().getComplianceRespCd());
				detailsRespEntity.setComplianceReason(billerResponseDto.getReason().getComplianceReason());

				billerDetailsRespRepository.save(detailsRespEntity);
				responseDTO.setMessage("Unable to fetch Biller");
				responseDTO.setStatusCode(201);
				responseDTO.setData(billerResponseDto);
				log.info("<-------- BillerDetails.save >> Getting Failure response-------------->");

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
				responseDTO.setData(BillerDetailsResponse.class);
				throw new ServerNotAccessableExcption();
			}
		}
		return responseDTO;
	}
}