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
import com.anniyamtech.bbps.repository.BillValidationRepository;
import com.anniyamtech.bbps.repository.BillValidationRespRepository;
import com.anniyamtech.bbps.requestDto.BankReqDto;
import com.anniyamtech.bbps.requestDto.BillValidationCustomerParamsReqDto;
import com.anniyamtech.bbps.requestDto.BillValidationRequest;
import com.anniyamtech.bbps.requestEntity.BillValidationBillDetails;
import com.anniyamtech.bbps.requestEntity.BillValidationCustomerParams;
import com.anniyamtech.bbps.requestEntity.BillValidationEntity;
import com.anniyamtech.bbps.responseDto.BankResponseDto;
import com.anniyamtech.bbps.responseDto.BillValidationResponse;
import com.anniyamtech.bbps.responseEntity.BillValidationResponseEntity;
import com.anniyamtech.bbps.service.BillValidationService;
import com.anniyamtech.bbps.utils.BbpsAes;
import com.anniyamtech.bbps.utils.JulianDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BillValidationServiceImpl implements BillValidationService {
	@Value("${kotakbank.web.billValidation}")
	String billValidationUrl;

	@Value("${agentInstitutionId}")
	String agentId;
	@Autowired
	BillValidationRepository billValidationRepository;
	@Autowired
	BbpsAes bbps;
	@Autowired
	BillValidationRespRepository validationRespRepository;
	BankReqDto bankReq = new BankReqDto();
	Gson gson = new Gson();
	BillValidationBillDetails billDetails = new BillValidationBillDetails();
	BillValidationResponseEntity validationResponseEntity = new BillValidationResponseEntity();

	BaseDTO responseDTO = new BaseDTO();

	@Override
	public BaseDTO billValidation(BillValidationRequest billValidationRequest) throws Exception {
		try {
			log.info("<-------- BillValidation.service >> Started-------------->");
			String refId = JulianDateConverter.generationLogic();
			String date = JulianDateConverter.dateFormatter();
			billValidationRequest.getHead().setTs(date);

			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			BillValidationEntity billValidationEntity = new BillValidationEntity();
			billValidationRequest.getHead().setRefId(refId);
			billValidationEntity.setVer(billValidationRequest.getHead().getVer());
			billValidationEntity.setTs(date);
			billValidationEntity.setOrigInst(billValidationRequest.getHead().getOrigInst());
			billValidationEntity.setRefId(refId);
			billValidationEntity.setAgentId(billValidationRequest.getAgentId());
			billDetails.setBillerId(billValidationRequest.getBillDetails().getBillerId());
			billValidationEntity.setBillDetails(billDetails);

			List<BillValidationCustomerParamsReqDto> cpt = new ArrayList<BillValidationCustomerParamsReqDto>();
			billValidationRequest.getBillDetails().getCustomerParams();
			// customerParamsDto.add();
			List<BillValidationCustomerParams> customerParams = new ArrayList<BillValidationCustomerParams>();
			for (BillValidationCustomerParamsReqDto customerParamsDto : billValidationRequest.getBillDetails()
					.getCustomerParams()) {
				cpt.add(customerParamsDto);
				BillValidationCustomerParams cp = new BillValidationCustomerParams();
				cp.setName(customerParamsDto.getName());
				cp.setValue(customerParamsDto.getValue());
				customerParams.add(cp);
			}
			billDetails.setCustomerParams(customerParams);
			billValidationRepository.save(billValidationEntity);
			String requestBody = gson.toJson(billValidationRequest);
			log.info("<====Request to Json:" + requestBody);

			String EncryptedData = bbps.encryption(requestBody);
			log.info("<====Encrypted request:" + EncryptedData);

			bankReq.setRequestMsg(EncryptedData);
			bankReq.setAgentInstitutionId(agentId);
			String bankReqJson = gson.toJson(bankReq);
			log.info("<====Bank Request to Json:" + bankReqJson);

			BbpsApplication.disableSSL();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> entity = new HttpEntity<String>(bankReqJson, headers);
			log.info("<====Final Request:" + entity);

			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.postForObject(billValidationUrl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto biller = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + biller);

			String DecryptedData = bbps.decryption(biller.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);


			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			BillValidationResponse billValidationResponse = gson.fromJson(json, BillValidationResponse.class);
			log.info("<====Converted Json Response:" + billValidationResponse);

			if (billValidationResponse.getReason().getResponseCode().equals("000")) {
				validationResponseEntity.setRefId(billValidationResponse.getHead().getRefId());
				validationResponseEntity.setOrigInst(billValidationResponse.getHead().getOrigInst());
				validationResponseEntity.setTs(billValidationResponse.getHead().getTs());
				validationResponseEntity.setVer(billValidationResponse.getHead().getVer());
				validationResponseEntity.setApprovalRefNum(billValidationResponse.getReason().getApprovalRefNum());
				validationResponseEntity.setComplianceReason(billValidationResponse.getReason().getComplianceReason());
				validationResponseEntity.setComplianceRespCd(billValidationResponse.getReason().getComplianceRespCd());
				validationResponseEntity.setResponseCode(billValidationResponse.getReason().getResponseCode());
				validationResponseEntity.setResponseReason(billValidationResponse.getReason().getResponseReason());
				validationRespRepository.save(validationResponseEntity);

				responseDTO.setMessage("Bill validation validated Successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(billValidationResponse);
				log.info("<-------- BillValidation.save >> Ended-------------->");
			}

			else {
				validationResponseEntity.setRefId(billValidationResponse.getHead().getRefId());
				validationResponseEntity.setOrigInst(billValidationResponse.getHead().getOrigInst());
				validationResponseEntity.setTs(billValidationResponse.getHead().getTs());
				validationResponseEntity.setVer(billValidationResponse.getHead().getVer());
				validationResponseEntity.setComplianceReason(billValidationResponse.getReason().getComplianceReason());
				validationResponseEntity.setComplianceRespCd(billValidationResponse.getReason().getComplianceRespCd());

				validationRespRepository.save(validationResponseEntity);
				responseDTO.setMessage("Unable to validate Biller");
				responseDTO.setStatusCode(201);
				responseDTO.setData(billValidationResponse);
				log.info("<-------- BillValidation.save>> Getting Failure response-------------->");

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
				responseDTO.setData(BillValidationResponse.class);
				throw new ServerNotAccessableExcption();
			}
		}
		return responseDTO;
	}

}
