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
import com.anniyamtech.bbps.repository.BillPaymentReqRepository;
import com.anniyamtech.bbps.repository.BillFetchIdRepository;
import com.anniyamtech.bbps.repository.BillPaymentRespRepository;
import com.anniyamtech.bbps.requestDto.BankBranchBankRequestDto;
import com.anniyamtech.bbps.requestDto.BillPaymentAnalyticsRequestDto;
import com.anniyamtech.bbps.requestDto.BillPaymentCustomerParamsRequestDto;
import com.anniyamtech.bbps.requestDto.BillPaymentDeviceRequestDto;
import com.anniyamtech.bbps.requestDto.BillPaymentPaymentInformationRequestDto;
import com.anniyamtech.bbps.requestDto.BillPaymentRequestDto;
import com.anniyamtech.bbps.requestDto.BillPaymentRiskScoresRequestDto;
import com.anniyamtech.bbps.requestDto.BillPaymentTagsRequestDto;
import com.anniyamtech.bbps.requestEntity.BillPaymentAgent;
import com.anniyamtech.bbps.requestEntity.BillPaymentAnalytics;
import com.anniyamtech.bbps.requestEntity.BillPaymentBillDetails;
import com.anniyamtech.bbps.requestEntity.BillPaymentReqEntity;
import com.anniyamtech.bbps.requestEntity.BillPaymentCustomer;
import com.anniyamtech.bbps.requestEntity.BillPaymentCustomerParams;
import com.anniyamtech.bbps.requestEntity.BillPaymentCustomerTags;
import com.anniyamtech.bbps.requestEntity.BillPaymentDevice;
import com.anniyamtech.bbps.requestEntity.BillPaymentPaymentInformation;
import com.anniyamtech.bbps.requestEntity.BillPaymentRiskScores;
import com.anniyamtech.bbps.requestEntity.BillFetch;
import com.anniyamtech.bbps.responseDto.BankResponseDto;
import com.anniyamtech.bbps.responseDto.BillPaymentRespBillerResponseTagsDto;
import com.anniyamtech.bbps.responseDto.BillPaymentRespCustomerParamsDto;
import com.anniyamtech.bbps.responseDto.BillPaymentResponse;
import com.anniyamtech.bbps.responseEntity.BillPaymentCustomerParamsResponse;
import com.anniyamtech.bbps.responseEntity.BillPaymentRespEntity;
import com.anniyamtech.bbps.responseEntity.BillPaymentTagsResponse;
import com.anniyamtech.bbps.service.BankBranchBillPaymentService;
import com.anniyamtech.bbps.utils.BbpsAes;
import com.anniyamtech.bbps.utils.JulianDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BillPaymentServiceImpl implements BankBranchBillPaymentService {
	@Value("${kotakbank.web.billPayment}")
	String billerPaymentUrl;

	@Value("${agentInstitutionId}")
	String agentId;

	@Autowired
	BbpsAes bbps;
	@Autowired
	BillFetchIdRepository billFetchIdRepository;
	@Autowired
	BillPaymentRespRepository bankBranchRespRepository;
	BillPaymentRespEntity bankBranchRespEntity = new BillPaymentRespEntity();
	BillPaymentCustomer customer = new BillPaymentCustomer();
	BillPaymentAgent agent = new BillPaymentAgent();
	BillPaymentBillDetails billDetails = new BillPaymentBillDetails();


	@Autowired
	BillPaymentReqRepository bankBranchBillPaymentRepository;
	BankBranchBankRequestDto bankReq = new BankBranchBankRequestDto();

	Gson gson = new Gson();
	BaseDTO responseDTO = new BaseDTO();

	@Override
	public BaseDTO billPayment(BillPaymentRequestDto billPaymentReqest) throws Exception {
		try {
			log.info("<-------- BillPayment.service >> Started-------------->");
			BillPaymentReqEntity billPayment = new BillPaymentReqEntity();
			String date = JulianDateConverter.dateFormatter();
			billPaymentReqest.getHead().setTs(date);
			billPayment.setTs(date);

			BillFetch billFetch = billFetchIdRepository.getById(1);
			billPaymentReqest.getHead().setRefId(billFetch.getRefId());

			billPayment.setRefId(billPaymentReqest.getHead().getRefId());
			billPayment.setVer(billPaymentReqest.getHead().getVer());
			billPayment.setTs(billPaymentReqest.getHead().getTs());
			billPayment.setOrigInst(billPaymentReqest.getHead().getOrigInst());

			billPaymentReqest.getTxn().setTs(date);
			billPayment.setTs(date);
			String msgId = JulianDateConverter.generationLogic();
			billPaymentReqest.getTxn().setMsgId(msgId);
			billPayment.setMsgId(msgId);
			String txnReferenceId = JulianDateConverter.generationLogic2();
			billPaymentReqest.getTxn().setTxnReferenceId(txnReferenceId);
			billPayment.setTxnReferenceId(txnReferenceId);
			billPayment.setTs(billPaymentReqest.getTxn().getTs());
			String type = "FORWARD TYPE REQUEST";
			billPaymentReqest.getTxn().setType(type);
			billPayment.setType(type);
			log.info(">>>>>>>> refId: " + billFetch.getRefId());
			log.info(">>>>>>>> date: " + date);
			log.info(">>>>>>>> msgId: " + msgId);
			// Risk Score
			List<BillPaymentRiskScoresRequestDto> rs = new ArrayList<BillPaymentRiskScoresRequestDto>();
			List<BillPaymentRiskScores> riskScores = new ArrayList<BillPaymentRiskScores>();
			for (BillPaymentRiskScoresRequestDto riskScoresReqDto : billPaymentReqest.getTxn().getRiskScores()) {
				rs.add(riskScoresReqDto);
				BillPaymentRiskScores scores = new BillPaymentRiskScores();
				scores.setProvider(riskScoresReqDto.getProvider());
				scores.setType(riskScoresReqDto.getType());
				scores.setValue(riskScoresReqDto.getValue());
				riskScores.add(scores);
			}
			billPayment.setRiskScores(riskScores);

			// Analytics
			List<BillPaymentAnalyticsRequestDto> analytics = new ArrayList<BillPaymentAnalyticsRequestDto>();
			List<BillPaymentAnalytics> analyticsTag = new ArrayList<BillPaymentAnalytics>();
			for (BillPaymentAnalyticsRequestDto analyticsTagRequest : billPaymentReqest.getAnalytics()) {
				analytics.add(analyticsTagRequest);
				BillPaymentAnalytics billPaymentAnalytics = new BillPaymentAnalytics();
				billPaymentAnalytics.setName(analyticsTagRequest.getName());
				// analyticsTagRequest.setValue();

				billPaymentAnalytics.setValue(analyticsTagRequest.getValue());
				analyticsTag.add(billPaymentAnalytics);
			}
			billPayment.setAnalytics(analyticsTag);
			// Payment Information
			List<BillPaymentPaymentInformationRequestDto> informationReqDtos = new ArrayList<BillPaymentPaymentInformationRequestDto>();
			List<BillPaymentPaymentInformation> paymentInformations = new ArrayList<BillPaymentPaymentInformation>();
			for (BillPaymentPaymentInformationRequestDto informationReqDto : billPaymentReqest.getPaymentInformation()) {
				informationReqDtos.add(informationReqDto);
				BillPaymentPaymentInformation information = new BillPaymentPaymentInformation();
				information.setName(informationReqDto.getName());
				information.setValue(informationReqDto.getValue());
				paymentInformations.add(information);
			}
			billPayment.setPaymentInformation(paymentInformations);

			// Customer
			customer.setMobile(billPaymentReqest.getCustomer().getMobile());
			billPayment.setCustomer(customer);
			// Tags
			List<BillPaymentTagsRequestDto> tagsRq = new ArrayList<BillPaymentTagsRequestDto>();
			List<BillPaymentCustomerTags> tags = new ArrayList<BillPaymentCustomerTags>();
			for (BillPaymentTagsRequestDto TagsReqDto : billPaymentReqest.getCustomer().getTags()) {
				tagsRq.add(TagsReqDto);
				BillPaymentCustomerTags tg = new BillPaymentCustomerTags();
				tg.setName(TagsReqDto.getName());
				tg.setValue(TagsReqDto.getValue());
				tags.add(tg);
			}
			customer.setTags(tags);

			// Agent
			agent.setId(billPaymentReqest.getAgent().getId()); // agent >> id
			billPayment.setAgent(agent);
			// Device
			List<BillPaymentDeviceRequestDto> dvc = new ArrayList<BillPaymentDeviceRequestDto>();
			List<BillPaymentDevice> device = new ArrayList<BillPaymentDevice>(); // Device Entity
			for (BillPaymentDeviceRequestDto deviceReqDto : billPaymentReqest.getAgent().getDevice()) {
				dvc.add(deviceReqDto);
				BillPaymentDevice ad = new BillPaymentDevice();
				ad.setName(deviceReqDto.getName());
				ad.setValue(deviceReqDto.getValue());
				device.add(ad);
			}
			agent.setDevice(device);
			// Bill Details
			billDetails.setBillerId(billPaymentReqest.getBillDetails().getBillerId());
			billPayment.setBillDetails(billDetails);
			// CustomerParam
			List<BillPaymentCustomerParamsRequestDto> cpt = new ArrayList<BillPaymentCustomerParamsRequestDto>();
			List<BillPaymentCustomerParams> customerParams = new ArrayList<BillPaymentCustomerParams>();
			for (BillPaymentCustomerParamsRequestDto customerParamsDto : billPaymentReqest.getBillDetails()
					.getCustomerParams()) {
				cpt.add(customerParamsDto);
				BillPaymentCustomerParams cp = new BillPaymentCustomerParams();
				cp.setName(customerParamsDto.getName());
				cp.setValue(customerParamsDto.getValue());
				customerParams.add(cp);
			}
			billDetails.setCustomerParams(customerParams);
			// Amount
			billPayment.setAmount(billPaymentReqest.getAmount().getAmount());
			billPayment.setCustConvFee(billPaymentReqest.getAmount().getCustConvFee());
			billPayment.setCouCustConvFee(billPaymentReqest.getAmount().getCouCustConvFee());
			billPayment.setCurrency(billPaymentReqest.getAmount().getCurrency());
			billPayment.setSplitPayAmount(billPaymentReqest.getAmount().getSplitPayAmount());
			billPayment.setTags(billPaymentReqest.getAmount().getTags());
			// PaymentMethod
			billPayment.setQuickPay(billPaymentReqest.getPaymentMethod().getQuickPay());
			billPayment.setPaymentMode(billPaymentReqest.getPaymentMethod().getPaymentMode());
			billPayment.setSplitPay(billPaymentReqest.getPaymentMethod().getSplitPay());
			billPayment.setOffusPay(billPaymentReqest.getPaymentMethod().getOffusPay());
			// DebitAccount
			billPayment.setDebitAccountNo(billPaymentReqest.getDebitAccountNo());

			bankBranchBillPaymentRepository.save(billPayment);
			String requestBody = gson.toJson(billPaymentReqest);
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
			String response = restTemplate.postForObject(billerPaymentUrl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto biller1 = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + biller1);

			String DecryptedData = bbps.decryption(biller1.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);

			Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
			BillPaymentResponse billPaymentResponse = gson.fromJson(json, BillPaymentResponse.class);
			log.info("<====Converted Json Response:" + billPaymentResponse);

			if (billPaymentResponse.getReason().getResponseCode().equals("000")) {

				bankBranchRespEntity.setRefId(billPaymentResponse.getHead().getRefId());
				bankBranchRespEntity.setOrigInst(billPaymentResponse.getHead().getOrigInst());
				bankBranchRespEntity.setTs(billPaymentResponse.getHead().getTs());
				bankBranchRespEntity.setVer(billPaymentResponse.getHead().getVer());

				bankBranchRespEntity.setResponseCode(billPaymentResponse.getReason().getResponseCode());
				bankBranchRespEntity.setResponseReason(billPaymentResponse.getReason().getResponseReason());
				bankBranchRespEntity.setComplianceRespCd(billPaymentResponse.getReason().getComplianceReasonCd());
				bankBranchRespEntity.setComplianceReason(billPaymentResponse.getReason().getComplianceReason());
				bankBranchRespEntity.setApprovalRefNum(billPaymentResponse.getReason().getApprovalRefNum());
				bankBranchRespEntity.setMsgId(billPaymentResponse.getTxn().getMsgId());
				bankBranchRespEntity.setTxnReferenceId(billPaymentResponse.getTxn().getTxnReferenceId());
				bankBranchRespEntity.setTs(billPaymentResponse.getTxn().getTs());
				bankBranchRespEntity.setBillerId(billPaymentResponse.getBillDetails().getBillerId());
				bankBranchRespEntity.setAmount(billPaymentResponse.getBillerResponse().getAmount());
				bankBranchRespEntity.setBillDate(billPaymentResponse.getBillerResponse().getBillDate());
				bankBranchRespEntity.setCustomerName(billPaymentResponse.getBillerResponse().getCustomerName());
				bankBranchRespEntity.setDueDate(billPaymentResponse.getBillerResponse().getDueDate());
				bankBranchRespEntity.setBillPeriod(billPaymentResponse.getBillerResponse().getBillPeriod());
				bankBranchRespEntity.setBillNumber(billPaymentResponse.getBillerResponse().getBillNumber());
				// --------------------------------Customer params
				// ---------------------------------------------------------//
				List<BillPaymentRespCustomerParamsDto> customerParamsRespdto = new ArrayList<>();

				List<BillPaymentCustomerParamsResponse> customerParamsResp = new ArrayList<>();
				for (BillPaymentRespCustomerParamsDto paramsResp : billPaymentResponse.getBillDetails()
						.getCustomerParams()) {
					customerParamsRespdto.add(paramsResp);
					BillPaymentCustomerParamsResponse customerParamsResp2 = new BillPaymentCustomerParamsResponse();
					customerParamsResp2.setName(paramsResp.getName());
					customerParamsResp2.setValue(paramsResp.getValue());
					customerParamsResp.add(customerParamsResp2);
					bankBranchRespEntity.setCustomerParams(customerParamsResp);

				}

//----------------------------------------------biller Response------------------------------------//		
				if (billPaymentResponse.getBillerResponse().getTags() != null) {
					List<BillPaymentRespBillerResponseTagsDto> billertags = new ArrayList<>();

					List<BillPaymentTagsResponse> tags1 = new ArrayList<>();
					for (BillPaymentRespBillerResponseTagsDto tagsResp : billPaymentResponse.getBillerResponse()
							.getTags()) {
						billertags.add(tagsResp);
						BillPaymentTagsResponse tags2 = new BillPaymentTagsResponse();
						tags2.setName(tagsResp.getName());
						tags2.setValue(tagsResp.getValue());
						tags1.add(tags2);
					}
					bankBranchRespEntity.setTags(tags1);
					bankBranchRespRepository.save(bankBranchRespEntity);
				} else {
					System.out.println("tag is null");
					bankBranchRespRepository.save(bankBranchRespEntity);

				}
				responseDTO.setMessage("Bill Payment Bank Branch Fetched Successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(billPaymentResponse);
				log.info("<-------- BillPaymentBankBranch.save >> Ended-------------->");

			} else {
				bankBranchRespEntity.setRefId(billPaymentResponse.getHead().getRefId());
				bankBranchRespEntity.setOrigInst(billPaymentResponse.getHead().getOrigInst());
				bankBranchRespEntity.setTs(billPaymentResponse.getHead().getTs());
				bankBranchRespEntity.setVer(billPaymentResponse.getHead().getVer());

				bankBranchRespEntity.setResponseCode(billPaymentResponse.getReason().getResponseCode());
				bankBranchRespEntity.setResponseReason(billPaymentResponse.getReason().getResponseReason());
				bankBranchRespEntity.setComplianceRespCd(billPaymentResponse.getReason().getComplianceReasonCd());
				bankBranchRespEntity.setComplianceReason(billPaymentResponse.getReason().getComplianceReason());

				bankBranchRespEntity.setMsgId(billPaymentResponse.getTxn().getMsgId());
				bankBranchRespEntity.setTxnReferenceId(billPaymentResponse.getTxn().getTxnReferenceId());
				bankBranchRespEntity.setTs(billPaymentResponse.getTxn().getTs());
				bankBranchRespRepository.save(bankBranchRespEntity);
				responseDTO.setMessage("Unable to fetch Bill Payment Bank Branch");
				responseDTO.setStatusCode(201);
				responseDTO.setData(billPaymentResponse);
				log.info("<-------- BillPaymentBankBranch.save >> Getting Failure response-------------->");

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
				responseDTO.setData(BillPaymentResponse.class);
				throw new ServerNotAccessableExcption();

			}
		}

		return responseDTO;
	}
}
