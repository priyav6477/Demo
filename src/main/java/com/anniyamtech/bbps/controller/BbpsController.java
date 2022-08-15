package com.anniyamtech.bbps.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anniyamtech.bbps.config.BaseDTO;
import com.anniyamtech.bbps.requestDto.BillFetchRequestDto;
import com.anniyamtech.bbps.requestDto.BillPaymentRequestDto;
import com.anniyamtech.bbps.requestDto.BillValidationRequest;
import com.anniyamtech.bbps.requestDto.BillerDetailsRequestDto;
import com.anniyamtech.bbps.requestDto.BillerListReqDto;
import com.anniyamtech.bbps.requestDto.CheckComplaintStatusReqDto;
import com.anniyamtech.bbps.requestDto.RaiseComplaintReqDto;
import com.anniyamtech.bbps.requestDto.TransactionStatusMobile;
import com.anniyamtech.bbps.requestDto.TransactionStatusReq;
import com.anniyamtech.bbps.service.Impl.BillFetchServiceImpl;
import com.anniyamtech.bbps.service.Impl.BillPaymentServiceImpl;
import com.anniyamtech.bbps.service.Impl.BillValidationServiceImpl;
import com.anniyamtech.bbps.service.Impl.BillerDetailsServiceImpl;
import com.anniyamtech.bbps.service.Impl.BillerListServiceImp;
import com.anniyamtech.bbps.service.Impl.CheckComplaintStatusServiceImp;
import com.anniyamtech.bbps.service.Impl.RaiseComplaintServiceImpl;
import com.anniyamtech.bbps.service.Impl.TransactionStatusServiceImp;

@RestController
@RequestMapping(value = "/bbps")
public class BbpsController {
	// Logger Object
	private static final Logger log = LoggerFactory.getLogger(BbpsController.class);

	@Autowired
	BillerListServiceImp billerListServiceImp;

	@Autowired
	CheckComplaintStatusServiceImp complaintStatus;

	@Autowired
	TransactionStatusServiceImp txnStatus;

	@Autowired
	private BillFetchServiceImpl billFetchServiceImpl;

	@Autowired
	private BillerDetailsServiceImpl billerDetailsServiceImpl;

	@Autowired
	private BillValidationServiceImpl billValidationServiceImpl;

	@Autowired
	private RaiseComplaintServiceImpl raiseComplaint;

	@Autowired
	BillPaymentServiceImpl bankBranchBillPaymentServiceImp;

	@PostMapping("/BillPayment")
	public BaseDTO BillPaymentBankBranch(@RequestBody BillPaymentRequestDto bankBranchBillPaymentRequestDto)
			throws Exception {

		log.info("<--- BbpsController.BillPayment STARTED --->");

		BaseDTO billPaymentResponse = bankBranchBillPaymentServiceImp.billPayment(bankBranchBillPaymentRequestDto);
		
		log.info("<--- BbpsController.BillPayment END --->");

		return billPaymentResponse;
	}

	@PostMapping("/RaiseComplaint")
	public BaseDTO RaiseComplaintAgainstTransaction(@RequestBody RaiseComplaintReqDto raiseComplaintReqDto)
			throws Exception {
		BaseDTO baseDTO = new BaseDTO();
		log.info("<--- BbpsController.RaiseComplaint STARTED --->");
		if (raiseComplaintReqDto.getComplaintType().equalsIgnoreCase("Transaction")) {
			baseDTO = raiseComplaint.raiseComplaintAgainstTransaction(raiseComplaintReqDto);
		} else if (raiseComplaintReqDto.getComplaintType().equalsIgnoreCase("Service")) {
			if (raiseComplaintReqDto.getParticipationType().equalsIgnoreCase("AGENT")) {
				baseDTO = raiseComplaint.raiseComplaintAgainstAgent(raiseComplaintReqDto);
			} else if (raiseComplaintReqDto.getParticipationType().equalsIgnoreCase("Biller")) {
				baseDTO = raiseComplaint.raiseComplaintAgainstBiller(raiseComplaintReqDto);
			}
		}
		log.info("<--- BbpsController.RaiseComplaint END --->");
		return baseDTO;
	}

	@PostMapping("/BillValidation")
	public BaseDTO ValidateBill(@RequestBody BillValidationRequest billValidationRequest) throws Exception {

		log.info("<--- BbpsController.BillValidation create STARTED --->");
		BaseDTO billValidationResponse = billValidationServiceImpl.billValidation(billValidationRequest);
		log.info("<--- BbpsController.BillValidation create END --->");
		return billValidationResponse;
	}

	@PostMapping("/GetBillerDetails")
	public BaseDTO GetBillerDetails(@RequestBody BillerDetailsRequestDto billerDetailsReqDto) throws Exception {

		log.info("<--- BbpsController.BillerDetails STARTED --->");
		BaseDTO bankResponseDto = billerDetailsServiceImpl.billerDetails(billerDetailsReqDto);
		log.info("<--- BbpsController.BillerDetails END --->");
		return bankResponseDto;
	}

	// BillerList

	@PostMapping("/GetBillerList")
	public BaseDTO GetBillerList(@RequestBody BillerListReqDto billerListReqDto) throws Exception {

		log.info("<====== BbpsController.BillerList =====> Start");

		BaseDTO bankResponseDto = billerListServiceImp.billerList(billerListReqDto);

		log.info("<====== BbpsController.BillerList =====> End");
		return bankResponseDto;
	}

	// BillFetch

	@PostMapping("/BillFetch")
	public BaseDTO GetBillDetails(@RequestBody BillFetchRequestDto billFetchRequestDto) throws Exception {

		log.info("<--- BbpsController.BillFetch STARTED --->");
		BaseDTO billFetchResponse = billFetchServiceImpl.fetchBiller(billFetchRequestDto);
		log.info("<--- BbpsController.BillFetch END --->");

		return billFetchResponse;
	}

	// CheckComplaintStatus

	@PostMapping("/CheckComplaintStatus")
	public BaseDTO CheckComplaintStatus(@RequestBody CheckComplaintStatusReqDto complaintStatusReq) throws Exception {

		log.info("<====== BbpsController.CheckComplaintStatus  =====> Start");

		BaseDTO bankResponseDto = complaintStatus.checkingComplaintStatus(complaintStatusReq);

		log.info("<====== BbpsController.CheckComplaintStatus  =====> End");

		return bankResponseDto;
	}

	// Transaction Status UsingRefId
	@PostMapping("/TransactionStatusUsingRefId")
	public BaseDTO GetTransactionStatusUsingRefId(@RequestBody TransactionStatusReq txnReq) throws Exception {

		log.info("<====== BbpsController.TransactionStatusUsingRefId =====> Start");

		BaseDTO bankResponseDto = txnStatus.checkTransactionStatusUsingRefId(txnReq);
		log.info("<====== BbpsController.checkTransactionStatusUsingRefId =====> End");

		return bankResponseDto;
	}

	// TransactionStatus using Mobile
	@PostMapping("/TransactionStatusUsingMobile")
	public BaseDTO GetTransactionStatusUsingMobile(@RequestBody TransactionStatusMobile txnReq) throws Exception {

		log.info("<====== BbpsController.TransactionStatusMobile =====> Start");
		BaseDTO bankResponseDto = txnStatus.checkTransactionStatusUsingMobile(txnReq);
		log.info("<====== BbpsController.checkTransactionStatusUsingMobile =====> End");

		return bankResponseDto;
	}

}
