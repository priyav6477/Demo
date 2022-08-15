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
import com.anniyamtech.bbps.repository.BillFetchIdRepository;
import com.anniyamtech.bbps.repository.BillFetchRepository;
import com.anniyamtech.bbps.repository.BillFetchRespRepository;
import com.anniyamtech.bbps.requestDto.BankReqDto;
import com.anniyamtech.bbps.requestDto.BillFetchAnalyticsTagRequest;
import com.anniyamtech.bbps.requestDto.BillFetchRequestCustomerParams;
import com.anniyamtech.bbps.requestDto.BillFetchRequestCustomerTag;
import com.anniyamtech.bbps.requestDto.BillFetchRequestDeviceTag;
import com.anniyamtech.bbps.requestDto.BillFetchRequestDto;
import com.anniyamtech.bbps.requestDto.BillFetchRequestRiskScores;
import com.anniyamtech.bbps.requestEntity.BillFetch;
import com.anniyamtech.bbps.requestEntity.BillFetchAgent;
import com.anniyamtech.bbps.requestEntity.BillFetchAnalyticsTag;
import com.anniyamtech.bbps.requestEntity.BillFetchBillDetails;
import com.anniyamtech.bbps.requestEntity.BillFetchCustomerDetailsTag;
import com.anniyamtech.bbps.requestEntity.BillFetchCustomerParams;
import com.anniyamtech.bbps.requestEntity.BillFetchCustomerTags;
import com.anniyamtech.bbps.requestEntity.BillFetchDeviceTag;
import com.anniyamtech.bbps.requestEntity.BillFetchEntity;
import com.anniyamtech.bbps.requestEntity.BillFetchRiskScoresTag;
import com.anniyamtech.bbps.responseDto.BankResponseDto;
import com.anniyamtech.bbps.responseDto.BillFetchAdditionalInfo;
import com.anniyamtech.bbps.responseDto.BillFetchBillDetailsResponse;
import com.anniyamtech.bbps.responseDto.BillFetchBillerResponse;
import com.anniyamtech.bbps.responseDto.BillFetchCustomerParamsResp;
import com.anniyamtech.bbps.responseDto.BillFetchResponse;
import com.anniyamtech.bbps.responseDto.BillerResponseTags;
import com.anniyamtech.bbps.responseEntity.BillFetchAdditionalInfoRespEntity;
import com.anniyamtech.bbps.responseEntity.BillFetchBillerResponseTagsEntity;
import com.anniyamtech.bbps.responseEntity.BillFetchCustomerParamsRespEntity;
import com.anniyamtech.bbps.responseEntity.BillFetchRespEntity;
import com.anniyamtech.bbps.service.BillFetchService;
import com.anniyamtech.bbps.utils.BbpsAes;
import com.anniyamtech.bbps.utils.JulianDateConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BillFetchServiceImpl implements BillFetchService {
	@Value("${kotakbank.web.billFetch}")
	String billFetchUrl;

	@Value("${agentInstitutionId}")
	String agentId;

	@Autowired
	BillFetchRepository billFetchRepository;
	@Autowired
	BillFetchIdRepository billFetchIdRepository;
	@Autowired
	BillFetchRespRepository billFetchRespRepository;
	BillFetchCustomerDetailsTag billFetchCustomerDetailsTag = new BillFetchCustomerDetailsTag();

	BillFetchRespEntity billFetchRespEntity = new BillFetchRespEntity();
	BillFetchBillDetailsResponse billFetchBillDetailsResponse = new BillFetchBillDetailsResponse();
	BillFetchBillerResponse billFetchBillerResp = new BillFetchBillerResponse();
	@Autowired
	BbpsAes bbps;
	BankReqDto bankReq = new BankReqDto();
	Gson gson = new Gson();
	BillFetchAgent agent = new BillFetchAgent();
	BillFetchBillDetails billDetails = new BillFetchBillDetails();
	BaseDTO responseDTO = new BaseDTO();

	@Override
	public BaseDTO fetchBiller(BillFetchRequestDto billFetchRequestDto) throws Exception {
		// TODO Auto-generated method stub
		try {
			log.info("<-------- BillFetch.service >> Started-------------->");
			String refId = JulianDateConverter.generationLogic();
			String msgId = JulianDateConverter.generationLogic();
			String date = JulianDateConverter.dateFormatter();
			billFetchRequestDto.getHead().setTs(date);
			billFetchRequestDto.getTxn().setTs(date);
			// billFetchRequestDto.getAnalytics()
			log.info(">>>>>>>> refId: " + refId);
			log.info(">>>>>>>> date: " + date);
			log.info(">>>>>>>> msgId: " + msgId);
			BillFetchEntity billFetchEntity = new BillFetchEntity();
			billFetchRequestDto.getHead().setRefId(refId);
			billFetchRequestDto.getTxn().setMsgId(msgId);

			billFetchEntity.setVer(billFetchRequestDto.getHead().getVer());
			// billFetchEntity.setTs(billFetchRequestDto.getHead().getTs());
			billFetchEntity.setOrigInst(billFetchRequestDto.getHead().getOrigInst());
			billFetchEntity.setRefId(refId);
			billFetchEntity.setMsgId(msgId);
			billFetchCustomerDetailsTag.setMobile(billFetchRequestDto.getCustomer().getMobile());
			billFetchEntity.setCustomer(billFetchCustomerDetailsTag);
			agent.setId(billFetchRequestDto.getAgent().getId());
			billFetchEntity.setAgent(agent);
			billDetails.setBillerId(billFetchRequestDto.getBillDetails().getBillerId());
			billFetchEntity.setBillDetails(billDetails);
			List<BillFetchCustomerParams> customerParams = new ArrayList<BillFetchCustomerParams>();
			customerParams.add(new BillFetchCustomerParams());
			billFetchEntity.getBillDetails().setCustomerParams(customerParams);
			// billFetchRequestDto.getTxn().getRiskScores();
////---------------------------------------------------------------------------------------------////
			List<BillFetchRequestRiskScores> cpt = new ArrayList<BillFetchRequestRiskScores>();

			List<BillFetchRiskScoresTag> riskScores = new ArrayList<BillFetchRiskScoresTag>();
			for (BillFetchRequestRiskScores customerParamsDto : billFetchRequestDto.getTxn().getRiskScores()) {
				cpt.add(customerParamsDto);
				BillFetchRiskScoresTag cp = new BillFetchRiskScoresTag();
				cp.setProvider(customerParamsDto.getProvider());
				cp.setType(customerParamsDto.getType());
				cp.setValue(customerParamsDto.getValue());
				riskScores.add(cp);
			}
			billFetchEntity.setRiskScores(riskScores);
///----------------------------------------------------------------------------------////
			List<BillFetchAnalyticsTagRequest> analytics = new ArrayList<BillFetchAnalyticsTagRequest>();

			List<BillFetchAnalyticsTag> analyticsTag = new ArrayList<BillFetchAnalyticsTag>();
			for (BillFetchAnalyticsTagRequest analyticsTagRequest : billFetchRequestDto.getAnalytics()) {
				analytics.add(analyticsTagRequest);
				BillFetchAnalyticsTag billFetchAnalyticsTag = new BillFetchAnalyticsTag();

				billFetchAnalyticsTag.setName(analyticsTagRequest.getName());
				// analyticsTagRequest.setValue(date);
				billFetchAnalyticsTag.setValue(analyticsTagRequest.getValue());
				analyticsTag.add(billFetchAnalyticsTag);
			}
			billFetchEntity.setAnalytics(analyticsTag);
///----------------------------------------------------------------------------------////		
			List<BillFetchRequestCustomerTag> customer = new ArrayList<BillFetchRequestCustomerTag>();

			List<BillFetchCustomerTags> customerTag = new ArrayList<BillFetchCustomerTags>();
			for (BillFetchRequestCustomerTag customerTagRequest : billFetchRequestDto.getCustomer().getTags()) {
				customer.add(customerTagRequest);
				BillFetchCustomerTags billFetchCustomerTags = new BillFetchCustomerTags();

				billFetchCustomerTags.setName(customerTagRequest.getName());
				billFetchCustomerTags.setValue(customerTagRequest.getValue());
				customerTag.add(billFetchCustomerTags);
			}
			billFetchCustomerDetailsTag.setTags(customerTag);

///------------------------------------------------------------------------------////

			List<BillFetchRequestCustomerParams> requestCustomerParams = new ArrayList<BillFetchRequestCustomerParams>();
			List<BillFetchCustomerParams> billFetchCustomerParams = new ArrayList<BillFetchCustomerParams>();
			for (BillFetchRequestCustomerParams customerParamsDto : billFetchRequestDto.getBillDetails()
					.getCustomerParams()) {
				requestCustomerParams.add(customerParamsDto);
				BillFetchCustomerParams fetchCustomerParams = new BillFetchCustomerParams();
				fetchCustomerParams.setName(customerParamsDto.getName());
				fetchCustomerParams.setValue(customerParamsDto.getValue());
				billFetchCustomerParams.add(fetchCustomerParams);
			}
			billDetails.setCustomerParams(billFetchCustomerParams);
////--------------------------------------------------------------------------------////		
			List<BillFetchRequestDeviceTag> deviceTagReq = new ArrayList<BillFetchRequestDeviceTag>();
			List<BillFetchDeviceTag> fetchDeviceTags = new ArrayList<BillFetchDeviceTag>();
			for (BillFetchRequestDeviceTag deviceTagDto : billFetchRequestDto.getAgent().getDevice()) {
				deviceTagReq.add(deviceTagDto);
				BillFetchDeviceTag deviceTag = new BillFetchDeviceTag();
				deviceTag.setName(deviceTagDto.getName());
				deviceTag.setValue(deviceTagDto.getValue());
				fetchDeviceTags.add(deviceTag);
			}
			agent.setDevice(fetchDeviceTags);
			billFetchRepository.save(billFetchEntity);

			BillFetch billFetch = billFetchIdRepository.getById(1);

			billFetch.setRefId(billFetchEntity.getRefId());
			billFetchIdRepository.save(billFetch);
			String requestBody = gson.toJson(billFetchRequestDto);
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
			String response = restTemplate.postForObject(billFetchUrl, entity, String.class);
			log.info("<====Json response:" + response);

			BankResponseDto biller = gson.fromJson(response, BankResponseDto.class);
			log.info("<====Converted Json Response:" + biller);

			String DecryptedData = bbps.decryption(biller.getResponseMsg());
			log.info("<====Decrypted Response:" + DecryptedData);

			String json = DecryptedData;
			log.info("<====Decrypted Json Response:" + json);

			// String json =
			// "{\"head\":{\"refId\":\"hvHpCfomjOHDGVu03qlCjZP15ia20881231\",\"origInst\":\"KM21\",\"ts\":\"2022-03-29T12:31:35\",\"ver\":\"1.0\"},\"reason\":{\"responseCode\":\"000\",\"responseReason\":\"Successful\"},\"billers\":[]}";

			Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
			BillFetchResponse billFetchResponse = gson.fromJson(json, BillFetchResponse.class);
			log.info("<====Converted Json Response:" + billFetchResponse);

			if (billFetchResponse.getReason().getResponseCode().equals("000")) {
				billFetchRespEntity.setRefId(billFetchResponse.getHead().getRefId());
				billFetchRespEntity.setVer(billFetchResponse.getHead().getVer());
				billFetchRespEntity.setTs(billFetchResponse.getHead().getTs());
				billFetchRespEntity.setOrigInst(billFetchResponse.getHead().getOrigInst());
				billFetchRespEntity.setResponseCode(billFetchResponse.getReason().getResponseCode());
				billFetchRespEntity.setResponseReason(billFetchResponse.getReason().getResponseReason());
				billFetchRespEntity.setComplianceRespCd(billFetchResponse.getReason().getComplianceRespCd());
				billFetchRespEntity.setComplianceReason(billFetchResponse.getReason().getComplianceReason());
				billFetchRespEntity.setApprovalRefNum(billFetchResponse.getReason().getApporovalRefNum());
				billFetchRespEntity.setMsgId(billFetchResponse.getTxn().getMsgId());
				billFetchRespEntity.setBillerId(billFetchResponse.getBillDetails().getBillerId());
				billFetchRespEntity.setAmount(billFetchResponse.getBillerResponse().getAmount());
				billFetchRespEntity.setBillNumber(billFetchResponse.getBillerResponse().getBillNumber());
				billFetchRespEntity.setBillPeriod(billFetchResponse.getBillerResponse().getBillPeriod());
				billFetchRespEntity.setCustomerName(billFetchResponse.getBillerResponse().getCustomerName());
				billFetchRespEntity.setBillDate(billFetchResponse.getBillerResponse().getBillDate());
				billFetchRespEntity.setDueDate(billFetchResponse.getBillerResponse().getDueDate());
				billFetchRespEntity.setCustConvFee(billFetchResponse.getBillerResponse().getCustConvFee());
				billFetchRespEntity.setCustConvDesc(billFetchResponse.getBillerResponse().getCustConvDesc());
//---------------------------------------Customer params ----------------------------------------//		
				List<BillFetchCustomerParamsResp> customerParamsRespdto = new ArrayList<>();

				List<BillFetchCustomerParamsRespEntity> customerParamsResp = new ArrayList<>();
				for (BillFetchCustomerParamsResp paramsResp : billFetchResponse.getBillDetails().getCustomerParams()) {
					customerParamsRespdto.add(paramsResp);
					BillFetchCustomerParamsRespEntity customerParamsResp2 = new BillFetchCustomerParamsRespEntity();
					customerParamsResp2.setName(paramsResp.getName());
					customerParamsResp2.setValue(paramsResp.getValue());
					customerParamsResp.add(customerParamsResp2);
				}
				billFetchRespEntity.setCustomerParams(customerParamsResp);
//-------------------------------------------biller Response------------------------------------//		
				List<BillerResponseTags> billertags = new ArrayList<>();

				List<BillFetchBillerResponseTagsEntity> tags = new ArrayList<>();
				for (BillerResponseTags tagsResp : billFetchResponse.getBillerResponse().getTags()) {
					billertags.add(tagsResp);
					BillFetchBillerResponseTagsEntity tags2 = new BillFetchBillerResponseTagsEntity();
					tags2.setName(tagsResp.getName());
					tags2.setValue(tagsResp.getValue());
					tags.add(tags2);
				}
				billFetchRespEntity.setTags(tags);
////-------------------------------------------additional info---------------------------------//
				List<BillFetchAdditionalInfo> additionalInfo = new ArrayList<>();
				List<BillFetchAdditionalInfoRespEntity> additionalInforesp = new ArrayList<>();
				for (BillFetchAdditionalInfo additionalInfoResp : billFetchResponse.getAdditionalInfoList()) {
					additionalInfo.add(additionalInfoResp);
					BillFetchAdditionalInfoRespEntity additionalInfo2 = new BillFetchAdditionalInfoRespEntity();
					additionalInfo2.setName(additionalInfoResp.getName());
					additionalInfo2.setValue(additionalInfoResp.getValue());

					additionalInforesp.add(additionalInfo2);
				}
				billFetchRespEntity.setAdditionalInfoList(additionalInforesp);

				billFetchRespRepository.save(billFetchRespEntity);

				responseDTO.setMessage("Biller List Fetched Successfully");
				responseDTO.setStatusCode(200);
				responseDTO.setData(billFetchResponse);
				log.info("<-------- BillFetch.service >> Ended-------------->");
			}

			else {
				billFetchRespEntity.setRefId(billFetchResponse.getHead().getRefId());
				billFetchRespEntity.setVer(billFetchResponse.getHead().getVer());
				billFetchRespEntity.setTs(billFetchResponse.getHead().getTs());
				billFetchRespEntity.setOrigInst(billFetchResponse.getHead().getOrigInst());
				billFetchRespEntity.setResponseCode(billFetchResponse.getReason().getResponseCode());
				billFetchRespEntity.setResponseReason(billFetchResponse.getReason().getResponseReason());
				billFetchRespEntity.setComplianceRespCd(billFetchResponse.getReason().getComplianceRespCd());
				billFetchRespEntity.setComplianceReason(billFetchResponse.getReason().getComplianceReason());

				billFetchRespRepository.save(billFetchRespEntity);
				responseDTO.setMessage("Unable to fetch Biller");
				responseDTO.setStatusCode(201);
				responseDTO.setData(billFetchResponse);
				log.info("<-------- BillerFetch.service >> Getting Failure response-------------->");

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
				responseDTO.setData(BillFetchResponse.class);
				throw new ServerNotAccessableExcption();

			}
		}

		return responseDTO;

	}
}
