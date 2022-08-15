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
import com.anniyamtech.bbps.repository.BillPaymentReqRepository;
import com.anniyamtech.bbps.repository.BillPaymentRespRepository;
import com.anniyamtech.bbps.repository.RaiseComplaintRepository;
import com.anniyamtech.bbps.repository.RaiseComplaintRespRepository;
import com.anniyamtech.bbps.requestDto.BankReqDto;
import com.anniyamtech.bbps.requestDto.RaiseComplaintComplaintDetailsReq;
import com.anniyamtech.bbps.requestDto.RaiseComplaintHeadReq;
import com.anniyamtech.bbps.requestDto.RaiseComplaintReqDto;
import com.anniyamtech.bbps.requestDto.RaiseComplaintRequestdto;
import com.anniyamtech.bbps.requestDto.RaiseComplaintTransactionReq;
import com.anniyamtech.bbps.requestDto.RaiseComplaintTransactionRequest;
import com.anniyamtech.bbps.requestDto.RaiseComplaintTransactionTxnReq;
import com.anniyamtech.bbps.requestDto.RaiseComplaintTxnHeadReq;
import com.anniyamtech.bbps.requestDto.RaiseComplaintTxncomplaintDetailsReq;
import com.anniyamtech.bbps.requestEntity.BillPaymentReqEntity;
import com.anniyamtech.bbps.requestEntity.RaiseComplaintRequestEntity;
import com.anniyamtech.bbps.responseDto.BankResponseDto;
import com.anniyamtech.bbps.responseDto.RaiseComplaintResponse;
import com.anniyamtech.bbps.responseEntity.BillPaymentRespEntity;
import com.anniyamtech.bbps.responseEntity.RaiseComplaintResponseEntity;
import com.anniyamtech.bbps.service.RaiseComplaintService;
import com.anniyamtech.bbps.utils.BbpsAes;
import com.anniyamtech.bbps.utils.JulianDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RaiseComplaintServiceImpl implements RaiseComplaintService {
	@Value("${kotakbank.web.raiseComplaint}")
	String raiseComplainturl;

	@Value("${agentInstitutionId}")
	String agentId;

	@Autowired
	BillPaymentRespRepository billPaymentRespRepository;
	@Autowired
	RaiseComplaintRepository raiseComplaintRepository;
	@Autowired
	BbpsAes bbps;
	@Autowired
	BillPaymentReqRepository billPaymentReqRepository;
	@Autowired
	RaiseComplaintRespRepository raiseComplaintRespRepository;
	
	
	RaiseComplaintResponseEntity raiseComplaintResponseEntity = new RaiseComplaintResponseEntity();

	BankReqDto bankReq = new BankReqDto();
	RaiseComplaintHeadReq head = new RaiseComplaintHeadReq();

	Gson gson = new Gson();
	BaseDTO responseDTO = new BaseDTO();

	//RaiseComplaintComplaintDetailsReq agentComplaintDetailsReq = new RaiseComplaintComplaintDetailsReq();

	@Override
	public BaseDTO raiseComplaintAgainstAgent(RaiseComplaintReqDto requestdto) throws Exception {
		// TODO Auto-generated method stub
		try {
			log.info("<-------- RaiseComplaintAgentComplaintDetails.save >> Started-------------->");
			RaiseComplaintRequestEntity raiseComplaintAgainstAgentEntity = new RaiseComplaintRequestEntity();
			RaiseComplaintRequestdto request = new RaiseComplaintRequestdto();
			RaiseComplaintHeadReq headReq = new RaiseComplaintHeadReq();
			RaiseComplaintTransactionReq txnReq = new RaiseComplaintTransactionReq();
			RaiseComplaintComplaintDetailsReq complaintreq = new RaiseComplaintComplaintDetailsReq();

			String refId = JulianDateConverter.generationLogic();
			String msgId = JulianDateConverter.generationLogic();
			String date = JulianDateConverter.dateFormatter();
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			log.info(">>>>>>>> msgId: " + msgId);
			headReq.setTs(date);
			headReq.setRefId(refId);
			headReq.setOrigInst("KM21");
			headReq.setVer("1.0");
			request.setHead(headReq);

			txnReq.setXchangeId("501");
			txnReq.setMsgId(msgId);
			txnReq.setTs(date);
			request.setTxn(txnReq);

			BillPaymentReqEntity billPaymentReqEntity = billPaymentReqRepository
					.findBytxnReferenceId(requestdto.getTxnReferenceId());

			complaintreq.setComplaintType(requestdto.getComplaintType());
			complaintreq.setDescription(requestdto.getDescription());
			complaintreq.setAgentId(billPaymentReqEntity.getAgent().getId());
			complaintreq.setParticipationType(requestdto.getTxnReferenceId());
			complaintreq.setServReason(requestdto.getServReason());
			request.setComplaintDetails(complaintreq);

			raiseComplaintAgainstAgentEntity.setTs(date);
			raiseComplaintAgainstAgentEntity.setRefId(refId);
			raiseComplaintAgainstAgentEntity.setMsgId(msgId);
			raiseComplaintAgainstAgentEntity.setTxnReferenceId(requestdto.getTxnReferenceId());
			raiseComplaintAgainstAgentEntity.setXchangeId(request.getTxn().getXchangeId());
			raiseComplaintAgainstAgentEntity.setComplaintType(requestdto.getComplaintType());
			raiseComplaintAgainstAgentEntity.setParticipationType(requestdto.getParticipationType());
			raiseComplaintAgainstAgentEntity.setAgentId(billPaymentReqEntity.getAgent().getId());
			raiseComplaintAgainstAgentEntity.setServReason(requestdto.getServReason());
			raiseComplaintAgainstAgentEntity.setDescription(requestdto.getDescription());
			raiseComplaintAgainstAgentEntity.setOtp(request.getOtp());
			raiseComplaintAgainstAgentEntity.setVer(request.getHead().getVer());
			raiseComplaintAgainstAgentEntity.setOrigInst(request.getHead().getOrigInst());
			raiseComplaintRepository.save(raiseComplaintAgainstAgentEntity);
			String requestBody = gson.toJson(request);
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
			String response = restTemplate.postForObject(raiseComplainturl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto againstAgentBankResp = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + againstAgentBankResp);

			String DecryptedData = bbps.decryption(againstAgentBankResp.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);

			Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
			RaiseComplaintResponse raiseComplaintAgainstAgentResponse = gson.fromJson(json,
					RaiseComplaintResponse.class);
			log.info("<====Converted Json Response:" + raiseComplaintAgainstAgentResponse);

			if (raiseComplaintAgainstAgentResponse.getReason().getResponseCode().equals("000")) {

				raiseComplaintResponseEntity.setRefId(raiseComplaintAgainstAgentResponse.getHead().getRefId());
				raiseComplaintResponseEntity.setVer(raiseComplaintAgainstAgentResponse.getHead().getVer());
				raiseComplaintResponseEntity
						.setOrigInst(raiseComplaintAgainstAgentResponse.getHead().getOrigInst());
				raiseComplaintResponseEntity.setTs(raiseComplaintAgainstAgentResponse.getHead().getTs());
				raiseComplaintResponseEntity.setMsgId(raiseComplaintAgainstAgentResponse.getTxn().getMsgId());
				raiseComplaintResponseEntity
						.setXchangeId(raiseComplaintAgainstAgentResponse.getTxn().getXchangeId());
				raiseComplaintResponseEntity
						.setComplaintId(raiseComplaintAgainstAgentResponse.getComplaintDetails().getComplaintId());
				raiseComplaintResponseEntity
						.setResponseCode(raiseComplaintAgainstAgentResponse.getReason().getResponseCode());
				raiseComplaintResponseEntity
						.setResponseReason(raiseComplaintAgainstAgentResponse.getReason().getResponseReason());
				raiseComplaintResponseEntity
						.setAssigned(raiseComplaintAgainstAgentResponse.getComplaintDetails().getAssigned());
				raiseComplaintResponseEntity.setTs(raiseComplaintAgainstAgentResponse.getTxn().getTs());

				raiseComplaintRespRepository.save(raiseComplaintResponseEntity);
				responseDTO.setMessage("Complaint raised against Agent successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(raiseComplaintAgainstAgentResponse);
				log.info("<-------- RaiseComplaintAgentComplaintDetails.save>> Ended-------------->");
			}

			else {
				raiseComplaintResponseEntity.setRefId(raiseComplaintAgainstAgentResponse.getHead().getRefId());
				raiseComplaintResponseEntity.setVer(raiseComplaintAgainstAgentResponse.getHead().getVer());
				raiseComplaintResponseEntity
						.setOrigInst(raiseComplaintAgainstAgentResponse.getHead().getOrigInst());
				raiseComplaintResponseEntity.setTs(raiseComplaintAgainstAgentResponse.getHead().getTs());
				raiseComplaintResponseEntity.setMsgId(raiseComplaintAgainstAgentResponse.getTxn().getMsgId());
				raiseComplaintResponseEntity
						.setXchangeId(raiseComplaintAgainstAgentResponse.getTxn().getXchangeId());
				raiseComplaintResponseEntity
						.setResponseCode(raiseComplaintAgainstAgentResponse.getReason().getResponseCode());
				raiseComplaintResponseEntity
						.setResponseReason(raiseComplaintAgainstAgentResponse.getReason().getResponseReason());
				raiseComplaintResponseEntity.setTs(raiseComplaintAgainstAgentResponse.getTxn().getTs());
				raiseComplaintResponseEntity
						.setComplianceRespCd(raiseComplaintAgainstAgentResponse.getReason().getComplianceRespCd());
				raiseComplaintResponseEntity
						.setComplianceReason(raiseComplaintAgainstAgentResponse.getReason().getComplianceReason());
				raiseComplaintResponseEntity.setMsgId(raiseComplaintAgainstAgentResponse.getTxn().getMsgId());
				raiseComplaintResponseEntity
						.setXchangeId(raiseComplaintAgainstAgentResponse.getTxn().getXchangeId());
				raiseComplaintResponseEntity.setTs(raiseComplaintAgainstAgentResponse.getTxn().getTs());

				raiseComplaintRespRepository.save(raiseComplaintResponseEntity);
				responseDTO.setMessage("Unable to raise complaint against agent");
				responseDTO.setStatusCode(201);
				responseDTO.setData(raiseComplaintAgainstAgentResponse);
				log.info(
						"<-------- RaiseComplaintAgentComplaintDetails.save >> Getting Failure response-------------->");

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
				responseDTO.setData(RaiseComplaintResponse.class);
				throw new ServerNotAccessableExcption();

			}
		}

		return responseDTO;
	}

	
	@Override
	public BaseDTO raiseComplaintAgainstBiller(RaiseComplaintReqDto reqBillerDto) throws Exception {
		try {
			log.info("<-------- RaiseComplaintBiller.Service >> Started-------------->");
			RaiseComplaintRequestEntity billerEntity = new RaiseComplaintRequestEntity();
			RaiseComplaintRequestdto request = new RaiseComplaintRequestdto();
			RaiseComplaintHeadReq headReq = new RaiseComplaintHeadReq();
			RaiseComplaintTransactionReq txnReq = new RaiseComplaintTransactionReq();
			RaiseComplaintComplaintDetailsReq complaintreq = new RaiseComplaintComplaintDetailsReq();

			String refId = JulianDateConverter.generationLogic();
			String msgId = JulianDateConverter.generationLogic();
			String date = JulianDateConverter.dateFormatter();
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			log.info(">>>>>>>> msgId: " + msgId);
			headReq.setTs(date);
			headReq.setRefId(refId);
			headReq.setOrigInst("KM21");
			headReq.setVer("1.0");
			request.setHead(headReq);

			txnReq.setXchangeId("501");
			txnReq.setMsgId(msgId);
			txnReq.setTs(date);
			request.setTxn(txnReq);

			BillPaymentRespEntity respEntity = billPaymentRespRepository
					.findBytxnReferenceId(reqBillerDto.getTxnReferenceId());
			complaintreq.setComplaintType(reqBillerDto.getComplaintType());
			complaintreq.setDescription(reqBillerDto.getDescription());
			complaintreq.setBillerId(respEntity.getBillerId());
			complaintreq.setParticipationType(reqBillerDto.getTxnReferenceId());
			complaintreq.setServReason(reqBillerDto.getServReason());
			request.setComplaintDetails(complaintreq);

			billerEntity.setTs(date);
			billerEntity.setRefId(refId);
			billerEntity.setMsgId(msgId);
			billerEntity.setTxnReferenceId(reqBillerDto.getTxnReferenceId());
			billerEntity.setXchangeId(request.getTxn().getXchangeId());
			billerEntity.setComplaintType(reqBillerDto.getComplaintType());
			billerEntity.setParticipationType(reqBillerDto.getParticipationType());

			billerEntity.setBillerId(respEntity.getBillerId());

			billerEntity.setServReason(reqBillerDto.getServReason());
			billerEntity.setDescription(reqBillerDto.getDescription());
			billerEntity.setOtp(request.getOtp());
			billerEntity.setVer(request.getHead().getVer());
			billerEntity.setOrigInst(request.getHead().getOrigInst());

			billerEntity.setOtp(request.getOtp());
			raiseComplaintRepository.save(billerEntity);

			String requestBody = gson.toJson(request);
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
			String response = restTemplate.postForObject(raiseComplainturl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto bankResponseDto = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + bankResponseDto);

			String DecryptedData = bbps.decryption(bankResponseDto.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			RaiseComplaintResponse complaintBillerResponse = gson.fromJson(json, RaiseComplaintResponse.class);
			log.info("<====Converted Json Response:" + complaintBillerResponse);

			if (complaintBillerResponse.getReason().getResponseCode().equals("000")) {
				raiseComplaintResponseEntity.setRefId(complaintBillerResponse.getHead().getRefId());
				raiseComplaintResponseEntity.setOrigInst(complaintBillerResponse.getHead().getOrigInst());
				raiseComplaintResponseEntity.setTs(complaintBillerResponse.getHead().getTs());
				raiseComplaintResponseEntity.setVer(complaintBillerResponse.getHead().getVer());
				raiseComplaintResponseEntity.setTs(complaintBillerResponse.getTxn().getTs());
				raiseComplaintResponseEntity.setMsgId(complaintBillerResponse.getTxn().getMsgId());
				raiseComplaintResponseEntity.setXchangeId(complaintBillerResponse.getTxn().getXchangeId());
				raiseComplaintResponseEntity.setResponseCode(complaintBillerResponse.getReason().getResponseCode());
				raiseComplaintResponseEntity.setResponseReason(complaintBillerResponse.getReason().getResponseReason());
				raiseComplaintResponseEntity.setAssigned(complaintBillerResponse.getComplaintDetails().getAssigned());
				raiseComplaintResponseEntity.setComplaintId(complaintBillerResponse.getComplaintDetails().getComplaintId());
				raiseComplaintRespRepository.save(raiseComplaintResponseEntity);
				responseDTO.setMessage("Complaint raised against Biller Successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(complaintBillerResponse);
				log.info("<-------- RaiseComplaintBiller.Service >> Ended-------------->");
			}

			else {
				raiseComplaintResponseEntity.setRefId(complaintBillerResponse.getHead().getRefId());
				raiseComplaintResponseEntity.setOrigInst(complaintBillerResponse.getHead().getOrigInst());
				raiseComplaintResponseEntity.setTs(complaintBillerResponse.getHead().getTs());
				raiseComplaintResponseEntity.setVer(complaintBillerResponse.getHead().getVer());
				raiseComplaintResponseEntity.setTs(complaintBillerResponse.getTxn().getTs());
				raiseComplaintResponseEntity.setMsgId(complaintBillerResponse.getTxn().getMsgId());
				raiseComplaintResponseEntity.setXchangeId(complaintBillerResponse.getTxn().getXchangeId());
				raiseComplaintResponseEntity.setResponseCode(complaintBillerResponse.getReason().getResponseCode());
				raiseComplaintResponseEntity.setResponseReason(complaintBillerResponse.getReason().getResponseReason());
				raiseComplaintResponseEntity.setComplianceReason(complaintBillerResponse.getReason().getComplianceReason());
				raiseComplaintResponseEntity.setComplianceRespCd(complaintBillerResponse.getReason().getComplianceRespCd());

				raiseComplaintResponseEntity.setTs(complaintBillerResponse.getTxn().getTs());
				raiseComplaintResponseEntity.setMsgId(complaintBillerResponse.getTxn().getMsgId());
				raiseComplaintResponseEntity.setXchangeId(complaintBillerResponse.getTxn().getXchangeId());

				raiseComplaintRespRepository.save(raiseComplaintResponseEntity);
				responseDTO.setMessage("Unable to raise complaint against biller");
				responseDTO.setStatusCode(201);
				responseDTO.setData(complaintBillerResponse);
				log.info("<-------- RaiseComplaintBiller.service >> Getting Failure response-------------->");

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
				responseDTO.setData(RaiseComplaintResponse.class);
				throw new ServerNotAccessableExcption();

			}
		}

		return responseDTO;
	}


	@Override
	public BaseDTO raiseComplaintAgainstTransaction(RaiseComplaintReqDto raiseComplaintReqDto) throws Exception {
		try {
			log.info("<-------- RaiseComplaintTransaction.Service >> Started-------------->");

			RaiseComplaintRequestEntity txnEntity = new RaiseComplaintRequestEntity();
			RaiseComplaintTxnHeadReq headReq = new RaiseComplaintTxnHeadReq();
			RaiseComplaintTransactionTxnReq txnReq = new RaiseComplaintTransactionTxnReq();
			RaiseComplaintTransactionRequest request = new RaiseComplaintTransactionRequest();
			RaiseComplaintTxncomplaintDetailsReq complaintreq = new RaiseComplaintTxncomplaintDetailsReq();

			String refId = JulianDateConverter.generationLogic();
			String msgId = JulianDateConverter.generationLogic();
			String txnReferenceId = JulianDateConverter.generationLogic2();
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> msgId: " + msgId);
			log.info(">>>>>>>> txnReferenceId: " + txnReferenceId);

			complaintreq.setComplaintType(raiseComplaintReqDto.getComplaintType());
			complaintreq.setDescription(raiseComplaintReqDto.getDescription());
			complaintreq.setDisposition(raiseComplaintReqDto.getServReason());
			complaintreq.setTxnReferenceId(raiseComplaintReqDto.getTxnReferenceId());
			request.setComplaintDetails(complaintreq);

			String date = JulianDateConverter.dateFormatter();
			log.info(">>>>>>>> date: " + date);

			headReq.setTs(date);
			headReq.setRefId(refId);
			headReq.setOrigInst("KM21");
			headReq.setVer("1.0");
			request.setHead(headReq);
			txnReq.setXchangeId("501");
			txnReq.setMsgId(msgId);
			txnReq.setTs(date);
			request.setTxn(txnReq);
			txnEntity.setRefId(refId);
			txnEntity.setMsgId(msgId);
			txnEntity.setVer(request.getHead().getVer());
			txnEntity.setOrigInst(request.getHead().getOrigInst());
			txnEntity.setTs(date);
			txnEntity.setXchangeId(request.getTxn().getXchangeId());
			txnEntity.setComplaintType(request.getComplaintDetails().getComplaintType());
			txnEntity.setDescription(request.getComplaintDetails().getDescription());
			txnEntity.setDisposition(request.getComplaintDetails().getDisposition());
			request.getComplaintDetails().setTxnReferenceId(txnReferenceId);
			txnEntity.setTxnReferenceId(txnReferenceId);
			txnEntity.setOtp(request.getOtp());
			raiseComplaintRepository.save(txnEntity);
	
			String requestBody = gson.toJson(request);
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
			String response = restTemplate.postForObject(raiseComplainturl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto bankResponseDto = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + bankResponseDto);

			String DecryptedData = bbps.decryption(bankResponseDto.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			RaiseComplaintResponse complaintTxnResponse = gson.fromJson(json, RaiseComplaintResponse.class);
			log.info("<====Converted Json Response:" + complaintTxnResponse);

			if (complaintTxnResponse.getReason().getResponseCode().equals("000")) {
				raiseComplaintResponseEntity.setRefId(complaintTxnResponse.getHead().getRefId());
				raiseComplaintResponseEntity.setOrigInst(complaintTxnResponse.getHead().getOrigInst());
				raiseComplaintResponseEntity.setTs(complaintTxnResponse.getHead().getTs());
				raiseComplaintResponseEntity.setVer(complaintTxnResponse.getHead().getVer());
				raiseComplaintResponseEntity.setTs(complaintTxnResponse.getTxn().getTs());
				raiseComplaintResponseEntity.setMsgId(complaintTxnResponse.getTxn().getMsgId());
				raiseComplaintResponseEntity.setXchangeId(complaintTxnResponse.getTxn().getXchangeId());
				raiseComplaintResponseEntity.setResponseCode(complaintTxnResponse.getReason().getResponseCode());
				raiseComplaintResponseEntity.setResponseReason(complaintTxnResponse.getReason().getResponseReason());
				raiseComplaintResponseEntity.setAssigned(complaintTxnResponse.getComplaintDetails().getAssigned());
				raiseComplaintResponseEntity.setComplaintId(complaintTxnResponse.getComplaintDetails().getComplaintId());
				raiseComplaintRespRepository.save(raiseComplaintResponseEntity);
				responseDTO.setMessage("Biller List Fetched Successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(complaintTxnResponse);
				log.info("<-------- RaiseComplaintTransaction.Service >> Ended-------------->");
			}

			else {
				raiseComplaintResponseEntity.setRefId(complaintTxnResponse.getHead().getRefId());
				raiseComplaintResponseEntity.setOrigInst(complaintTxnResponse.getHead().getOrigInst());
				raiseComplaintResponseEntity.setTs(complaintTxnResponse.getHead().getTs());
				raiseComplaintResponseEntity.setVer(complaintTxnResponse.getHead().getVer());
				raiseComplaintResponseEntity.setTs(complaintTxnResponse.getTxn().getTs());
				raiseComplaintResponseEntity.setMsgId(complaintTxnResponse.getTxn().getMsgId());
				raiseComplaintResponseEntity.setXchangeId(complaintTxnResponse.getTxn().getXchangeId());
				raiseComplaintResponseEntity.setResponseCode(complaintTxnResponse.getReason().getResponseCode());
				raiseComplaintResponseEntity.setResponseReason(complaintTxnResponse.getReason().getResponseReason());

				raiseComplaintRespRepository.save(raiseComplaintResponseEntity);

				responseDTO.setMessage("Unable to fetch Biller");
				responseDTO.setStatusCode(201);
				responseDTO.setData(complaintTxnResponse);
				log.info("<-------- RaiseComplaintTransaction.Service >> Getting Failure response-------------->");

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
				responseDTO.setData(RaiseComplaintResponse.class);
				throw new ServerNotAccessableExcption();

			}
		}

		return responseDTO;
	}
	
}