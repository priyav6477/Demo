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
import com.anniyamtech.bbps.repository.CheckComplaintStatusRepository;
import com.anniyamtech.bbps.repository.CheckComplaintStatusResponseRepository;
import com.anniyamtech.bbps.requestDto.BankReqDto;
import com.anniyamtech.bbps.requestDto.CheckComplaintStatusReqDto;
import com.anniyamtech.bbps.requestDto.Head;
import com.anniyamtech.bbps.requestDto.TransactionDetails;
import com.anniyamtech.bbps.requestDto.TxnDto;
import com.anniyamtech.bbps.requestEntity.CheckComplaintReq;
import com.anniyamtech.bbps.responseDto.BankResponseDto;
import com.anniyamtech.bbps.responseDto.CheckComplaintStatusResponseDto;
import com.anniyamtech.bbps.responseEntity.CheckComplaintResponse;
import com.anniyamtech.bbps.service.CheckComplaintStatusService;
import com.anniyamtech.bbps.utils.BbpsAes;
import com.anniyamtech.bbps.utils.JulianDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@Log4j2

@Service
public class CheckComplaintStatusServiceImp implements CheckComplaintStatusService {
	@Value("${kotakbank.web.CheckComplaintStatus}")
	String complaintStatusUrl;

	@Value("${agentInstitutionId}")
	String agentId;

	@Autowired
	BbpsAes bbps;

	@Autowired
	CheckComplaintStatusRepository checkComplaintStatusRepo;

	@Autowired
	CheckComplaintStatusResponseRepository ComplaintStatusResponseRepo;

	BankReqDto bankReq = new BankReqDto();
	Head head = new Head();
	TransactionDetails transactionDetails = new TransactionDetails();
	TxnDto txn = new TxnDto();

	Gson gson = new Gson();

	BaseDTO responseDTO = new BaseDTO();

	@Override
	public BaseDTO checkingComplaintStatus(CheckComplaintStatusReqDto checkComplaintReqDto) {

		try {

			// refId and msgId generation
			log.info("<-------- checkComplaintStatus.service >> Started-------------->");
			String refId = JulianDateConverter.generationLogic();
			checkComplaintReqDto.getHead().setRefId(refId);
			String msgId = JulianDateConverter.generationLogic();
			checkComplaintReqDto.getTxn().setMsgId(msgId);

			String date = JulianDateConverter.dateFormatter();

			checkComplaintReqDto.getHead().setTs(date);
			checkComplaintReqDto.getTxn().setTs(date);
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			log.info(">>>>>>>> msgId: " + msgId);

			CheckComplaintReq checkComplaintReq = new CheckComplaintReq();

			checkComplaintReq.setRefId(refId);
			checkComplaintReq.setVer(checkComplaintReqDto.getHead().getVer());
			checkComplaintReq.setOrigInst(checkComplaintReqDto.getHead().getOrigInst());
			checkComplaintReq.setTs(checkComplaintReqDto.getHead().getTs());
			checkComplaintReq.setMsgId(msgId);
			checkComplaintReq.setXchangeId(checkComplaintReqDto.getTxn().getXchangeId());
			checkComplaintReq.setComplaintId(checkComplaintReqDto.getComplaintDetails().getComplaintId());
			checkComplaintReq.setComplaintType(checkComplaintReqDto.getComplaintDetails().getComplaintType());
			log.info("<=====================" + checkComplaintReq + "====================>");

			checkComplaintStatusRepo.save(checkComplaintReq);

			// json conversion
			String requestBody = gson.toJson(checkComplaintReqDto);
			log.info("<====Request to Json:" + requestBody);

			// Encrypting req data
			String EncryptedData = bbps.encryption(requestBody);
			log.info("<====Encrypted request:" + EncryptedData);

			bankReq.setRequestMsg(EncryptedData);
			bankReq.setAgentInstitutionId(agentId);

			String bankReqJson = gson.toJson(bankReq);
			log.info("<====Bank Request to Json:" + bankReqJson);

			BbpsApplication.disableSSL();

			// sending data to bank url
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<String> entity = new HttpEntity<String>(bankReqJson, headers);
			log.info("<====Final Request:" + entity);

			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.postForObject(complaintStatusUrl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto bankResponse = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + bankResponse);

			String DecryptedData = bbps.decryption(bankResponse.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);

			Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
			log.info(" Decrypted Data : " + DecryptedData);

			CheckComplaintStatusResponseDto checkComplaintResponse = gson.fromJson(json,
					CheckComplaintStatusResponseDto.class);
			log.info("<====Converted Json Response:" + checkComplaintResponse);

			CheckComplaintResponse complaintStatus = new CheckComplaintResponse();

			if (checkComplaintResponse.getReason().getResponseCode().equals("000")) {

				complaintStatus.setRefId(checkComplaintResponse.getHead().getRefId());
				complaintStatus.setOrigInst(checkComplaintResponse.getHead().getOrigInst());
				complaintStatus.setVer(checkComplaintResponse.getHead().getVer());
				complaintStatus.setTs(checkComplaintResponse.getHead().getTs());
				complaintStatus.setResponseCode(checkComplaintResponse.getReason().getResponseCode());
				complaintStatus.setResponseReason(checkComplaintResponse.getReason().getResponseReason());
				complaintStatus.setMsgId(checkComplaintResponse.getTxn().getMsgId());
				complaintStatus.setXchangeId(checkComplaintResponse.getTxn().getXchangeId());
				complaintStatus.setAssigned(checkComplaintResponse.getComplaintDetails().getAssigned());
				complaintStatus.setComplaintId(checkComplaintResponse.getComplaintDetails().getComplaintId());
				complaintStatus.setComplaintStatus(checkComplaintResponse.getComplaintDetails().getComplaintStatus());
				complaintStatus.setRemarks(checkComplaintResponse.getComplaintDetails().getRemarks());

				ComplaintStatusResponseRepo.save(complaintStatus);

				responseDTO.setMessage("Complaint Status Fetched Successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(checkComplaintResponse);
				log.info("<-------- checkComplaintStatus.service >> Ended-------------->");
			} else {

				complaintStatus.setRefId(checkComplaintResponse.getHead().getRefId());
				complaintStatus.setOrigInst(checkComplaintResponse.getHead().getOrigInst());
				complaintStatus.setVer(checkComplaintResponse.getHead().getVer());
				complaintStatus.setTs(checkComplaintResponse.getHead().getTs());
				complaintStatus.setResponseCode(checkComplaintResponse.getReason().getResponseCode());
				complaintStatus.setResponseReason(checkComplaintResponse.getReason().getResponseReason());
				complaintStatus.setComplianceRespCd(checkComplaintResponse.getReason().getComplianceRespCd());
				complaintStatus.setComplianceReason(checkComplaintResponse.getReason().getComplianceReason());
				complaintStatus.setMsgId(checkComplaintResponse.getTxn().getMsgId());
				complaintStatus.setXchangeId(checkComplaintResponse.getTxn().getXchangeId());
				ComplaintStatusResponseRepo.save(complaintStatus);
				responseDTO.setMessage("Unable to fetch Complaint Status");
				responseDTO.setStatusCode(201);
				responseDTO.setData(checkComplaintResponse);
				log.info("<-------- checkComplaintStatus.service >> Getting failure response-------->");
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
