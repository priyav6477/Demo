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
import com.anniyamtech.bbps.repository.TransactionStatusRepository;
import com.anniyamtech.bbps.repository.TransactionStatusRepository2;
import com.anniyamtech.bbps.repository.TransactionStatusResponseRepository;
import com.anniyamtech.bbps.repository.TxnListRespository;
import com.anniyamtech.bbps.requestDto.BankReqDto;
import com.anniyamtech.bbps.requestDto.Head;
import com.anniyamtech.bbps.requestDto.TransactionDetails;
import com.anniyamtech.bbps.requestDto.TransactionStatusMobile;
import com.anniyamtech.bbps.requestDto.TransactionStatusReq;
import com.anniyamtech.bbps.requestDto.TxnDto;
import com.anniyamtech.bbps.requestEntity.TransactionStatus;
import com.anniyamtech.bbps.requestEntity.TransactionStatus2;
import com.anniyamtech.bbps.responseDto.BankResponseDto;
import com.anniyamtech.bbps.responseDto.CheckComplaintStatusResponseDto;
import com.anniyamtech.bbps.responseDto.TransactionStatusResponseDto;
import com.anniyamtech.bbps.responseDto.TxnList;
import com.anniyamtech.bbps.responseEntity.TransactionStatusResponse1;
import com.anniyamtech.bbps.responseEntity.TxnListResponse;
import com.anniyamtech.bbps.service.TransactionStatusService;
import com.anniyamtech.bbps.utils.BbpsAes;
import com.anniyamtech.bbps.utils.JulianDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TransactionStatusServiceImp implements TransactionStatusService {
	@Value("${kotakbank.web.transactionStatus}")
	String txnStatusUrl;

	@Value("${agentInstitutionId}")
	String agentId;

	@Autowired
	BbpsAes bbps;

	@Autowired
	TransactionStatusRepository transactionStatusRepo;
	
	@Autowired
	TransactionStatusRepository2 transactionStatusRepo2;


	@Autowired
	TransactionStatusResponseRepository responseRepository;

	@Autowired
	TxnListRespository txnListRespo;

	BankReqDto bankReq = new BankReqDto();
	Head head = new Head();
	TransactionDetails transactionDetails = new TransactionDetails();
	TxnDto txn = new TxnDto();
	BaseDTO responseDTO = new BaseDTO();
	Gson gson = new Gson();

	//using txnrefId
	@Override
	public BaseDTO checkTransactionStatusUsingRefId(TransactionStatusReq transactionReq) throws Exception {

		try {
			log.info("<-------- TransactionStatus.checkTransactionStatusUsingRefId >> Started-------------->");
			String refId = JulianDateConverter.generationLogic();
			transactionReq.getHead().setRefId(refId);
			String msgId = JulianDateConverter.generationLogic();
			transactionReq.getTxn().setMsgId(msgId);
			String txnReferenceId = JulianDateConverter.generationLogic2();
			transactionReq.getTransactionDetails().setTxnReferenceId(txnReferenceId);
			String date = JulianDateConverter.dateFormatter();
			transactionReq.getHead().setTs(date);
			transactionReq.getTxn().setTs(date);
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			log.info(">>>>>>>> msgId: " + msgId);
			log.info(">>>>>>>> txnReferenceId: " + txnReferenceId);

			TransactionStatus txnStatus = new TransactionStatus();

			txnStatus.setRefId(refId);
			txnStatus.setVer(transactionReq.getHead().getVer());
			txnStatus.setOrigInst(transactionReq.getHead().getOrigInst());
			txnStatus.setTs(date);
			txnStatus.setTxnReferenceId(txnReferenceId);
			txnStatus.setMsgId(msgId);
			txnStatus.setXchangeId(transactionReq.getTxn().getXchangeId());
			txnStatus.setOtp(transactionReq.getOtp());
			transactionStatusRepo.save(txnStatus);

			String requestBody = gson.toJson(transactionReq);
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
			String response = restTemplate.postForObject(txnStatusUrl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto bankResponse = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + bankResponse);

			String DecryptedData = bbps.decryption(bankResponse.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);

			Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
			TransactionStatusResponseDto txnResponse = gson.fromJson(json, TransactionStatusResponseDto.class);
			log.info("<====Converted Json Response:" + txnResponse);

			TransactionStatusResponse1 Transactionresponse1 = new TransactionStatusResponse1();

			if (txnResponse.getReason().getResponseCode().equals("000")) {

				// Transactionresponse1.setRefId(refId);
				Transactionresponse1.setRefId(txnResponse.getHead().getRefId());
				Transactionresponse1.setOrigInst(txnResponse.getHead().getOrigInst());
				Transactionresponse1.setTs(txnResponse.getHead().getTs());
				Transactionresponse1.setVer(txnResponse.getHead().getVer());
				Transactionresponse1.setResponseCode(txnResponse.getReason().getResponseCode());
				Transactionresponse1.setResponseReason(txnResponse.getReason().getResponseReason());
				Transactionresponse1.setMsgId(txnResponse.getTxn().getMsgId());
				Transactionresponse1.setXchangeId(txnResponse.getTxn().getXchangeId());
				Transactionresponse1.setMobile(txnResponse.getCustomerDetails().getMobile());

				 responseRepository.save(Transactionresponse1);

				List<TxnList> txnListDto = new ArrayList<TxnList>();

				for (TxnList txnList : txnResponse.getTxnList()) {
					txnListDto.add(txnList);

					TxnListResponse listResponse = new TxnListResponse();

					listResponse.setTxnReferenceId(txnList.getTxnReferenceId());
					listResponse.setAgentId(txnList.getAgentId());
					listResponse.setBillerId(txnList.getBillerId());
					listResponse.setAmount(txnList.getAmount());
					listResponse.setTxnDate(txnList.getTxnDate());
					listResponse.setTxnStatus(txnList.getTxnStatus());
					listResponse.setResponse1(Transactionresponse1);

					 txnListRespo.save(listResponse);

					responseDTO.setMessage("Transaction Status using RefId Fetched Successfully");
					responseDTO.setStatusCode(200);
					responseDTO.setData(txnResponse);
					log.info("<-------- TransactionStatus.checkTransactionStatusUsingRefId >> Ended-------------->");
				}
			} else {

				Transactionresponse1.setRefId(txnResponse.getHead().getRefId());
				Transactionresponse1.setOrigInst(txnResponse.getHead().getOrigInst());
				Transactionresponse1.setTs(txnResponse.getHead().getTs());
				Transactionresponse1.setVer(txnResponse.getHead().getVer());
				Transactionresponse1.setResponseCode(txnResponse.getReason().getResponseCode());
				Transactionresponse1.setResponseReason(txnResponse.getReason().getResponseReason());
				Transactionresponse1.setMsgId(txnResponse.getTxn().getMsgId());
				Transactionresponse1.setXchangeId(txnResponse.getTxn().getXchangeId());
				Transactionresponse1.setComplianceRespCd(txnResponse.getReason().getComplianceRespCd());
				Transactionresponse1.setComplianceReason(txnResponse.getReason().getComplianceReason());
				
				// Transactionresponse1.setMobile(txnResponse.getCustomerDetails().getMobile());

				responseRepository.save(Transactionresponse1);

				responseDTO.setMessage("Unable to fetch Transaction Status using mobile no ");
				responseDTO.setStatusCode(201);
				responseDTO.setData(txnResponse);
				log.info("<-------- TransactionStatus.checkTransactionStatusUsingRefId >> Getting failure response-------------->");
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

	//using mobile.no
	@Override
	public BaseDTO checkTransactionStatusUsingMobile(TransactionStatusMobile transactionReq) throws Exception {

		try {
			log.info("<-------- TransactionStatus.checkTransactionStatusUsingRefId >> Started-------------->");
			String refId = JulianDateConverter.generationLogic();
			transactionReq.getHead().setRefId(refId);
			String msgId = JulianDateConverter.generationLogic();
			transactionReq.getTxn().setMsgId(msgId);
			String date = JulianDateConverter.dateFormatter();
			transactionReq.getHead().setTs(date);
			transactionReq.getTxn().setTs(date);
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			log.info(">>>>>>>> msgId: " + msgId);
			TransactionStatus2 txnStatus = new TransactionStatus2();

			txnStatus.setRefId(refId);
			txnStatus.setVer(transactionReq.getHead().getVer());
			txnStatus.setOrigInst(transactionReq.getHead().getOrigInst());
			txnStatus.setTs(date);
			txnStatus.setMsgId(msgId);
			txnStatus.setXchangeId(transactionReq.getTxn().getXchangeId());
			txnStatus.setMobile(transactionReq.getTransactionDetails().getMobile());
			txnStatus.setFromDate(transactionReq.getTransactionDetails().getFromDate());
			txnStatus.setToDate(transactionReq.getTransactionDetails().getToDate());
			txnStatus.setOtp(transactionReq.getOtp());
			 transactionStatusRepo2.save(txnStatus);

			String requestBody = gson.toJson(transactionReq);
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
			String response = restTemplate.postForObject(txnStatusUrl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto bankResponse = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + bankResponse);

			String DecryptedData = bbps.decryption(bankResponse.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);
			Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
			TransactionStatusResponseDto txnResponse = gson.fromJson(json, TransactionStatusResponseDto.class);
			log.info("<====Converted Json Response:" + txnResponse);

			TransactionStatusResponse1 Transactionresponse1 = new TransactionStatusResponse1();

			if (txnResponse.getReason().getResponseCode().equals("000")) {

				// Transactionresponse1.setRefId(refId);
				Transactionresponse1.setRefId(txnResponse.getHead().getRefId());
				Transactionresponse1.setOrigInst(txnResponse.getHead().getOrigInst());
				Transactionresponse1.setTs(txnResponse.getHead().getTs());
				Transactionresponse1.setVer(txnResponse.getHead().getVer());
				Transactionresponse1.setResponseCode(txnResponse.getReason().getResponseCode());
				Transactionresponse1.setResponseReason(txnResponse.getReason().getResponseReason());
				Transactionresponse1.setMsgId(txnResponse.getTxn().getMsgId());
				Transactionresponse1.setXchangeId(txnResponse.getTxn().getXchangeId());
				Transactionresponse1.setMobile(txnResponse.getCustomerDetails().getMobile());

				 responseRepository.save(Transactionresponse1);

				List<TxnList> txnListDto = new ArrayList<TxnList>();

				for (TxnList txnList : txnResponse.getTxnList()) {
					txnListDto.add(txnList);

					TxnListResponse listResponse = new TxnListResponse();

					listResponse.setTxnReferenceId(txnList.getTxnReferenceId());
					listResponse.setAgentId(txnList.getAgentId());
					listResponse.setBillerId(txnList.getBillerId());
					listResponse.setAmount(txnList.getAmount());
					listResponse.setTxnDate(txnList.getTxnDate());
					listResponse.setTxnStatus(txnList.getTxnStatus());
					listResponse.setResponse1(Transactionresponse1);

					 txnListRespo.save(listResponse);

					responseDTO.setMessage("Transaction Status using Mobile Fetched Successfully");
					responseDTO.setStatusCode(200);
					responseDTO.setData(txnResponse);
					log.info("<-------- TransactionStatus.checkTransactionStatusUsingMobile >> Ended-------------->");

				}
			} else {

				Transactionresponse1.setRefId(txnResponse.getHead().getRefId());
				Transactionresponse1.setOrigInst(txnResponse.getHead().getOrigInst());
				Transactionresponse1.setTs(txnResponse.getHead().getTs());
				Transactionresponse1.setVer(txnResponse.getHead().getVer());
				Transactionresponse1.setResponseCode(txnResponse.getReason().getResponseCode());
				Transactionresponse1.setResponseReason(txnResponse.getReason().getResponseReason());
				Transactionresponse1.setMsgId(txnResponse.getTxn().getMsgId());
				Transactionresponse1.setXchangeId(txnResponse.getTxn().getXchangeId());
				Transactionresponse1.setComplianceRespCd(txnResponse.getReason().getComplianceRespCd());
				Transactionresponse1.setComplianceReason(txnResponse.getReason().getComplianceReason());
				
				// Transactionresponse1.setMobile(txnResponse.getCustomerDetails().getMobile());

				responseRepository.save(Transactionresponse1);

				responseDTO.setMessage("Unable to fetch Transaction Status using mobile no ");
				responseDTO.setStatusCode(201);
				responseDTO.setData(txnResponse);
				log.info("<-------- TransactionStatus.checkTransactionStatusUsingMobile >> Getting failure response-------------->");

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
