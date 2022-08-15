package com.anniyamtech.bbps.responseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "ts" }, allowSetters = true)
@Table(name = "bill_fetch_response")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class BillFetchRespEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "refid")
	private String refId;
	@Column(name = "version")
	private String ver;
	@Column(name = "ts")
	private String ts;
	@Column(name = "OrigInst")
	private String origInst;
	@Column(name = "approval_RefNum", length = 35)
	private String approvalRefNum;
	@Column(name = "response_Code")
	private String responseCode;
	@Column(name = "response_Reason")
	@Size(min = 7, max = 100)
	private String responseReason;
	@Column(name = "compliance_RespCd")
	private String complianceRespCd;
	@Column(name = "compliance_Reason")
	//@Size(min = 7, max = 20)
	private String complianceReason;
	@Column(name = "msgId", length = 35)
	private String msgId;
	
	@Column(name="biller_id")
	private String billerId;

	@ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "bill_fetch_customer_params_resp", joinColumns = @JoinColumn(name = "refid"))
	private List<BillFetchCustomerParamsRespEntity> customerParams = new ArrayList<BillFetchCustomerParamsRespEntity>();
	
	@Column(name = "customerName", length = 100)
	private String customerName;
	@Column(name = "amount", length = 20)
	private String amount;
	@Column(name = "dueDate", length = 10)
	private String dueDate;
	@Column(name = "billDate", length = 10)
	private String billDate;
	@Column(name = "billNumber")
	private String billNumber;
	@Column(name = "billPeriod", length = 20)
	private String billPeriod;
	@Column(name = "custConvFee")
	private String custConvFee;
	@Column(name = "custConvDesc")
	private String custConvDesc;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_fetch_biller_resp_tags", joinColumns = @JoinColumn(name = "refid"))
	private List<BillFetchBillerResponseTagsEntity>tags=new ArrayList<BillFetchBillerResponseTagsEntity>();

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_fetch_additional_info_resp", joinColumns = @JoinColumn(name = "refid"))
	private List<BillFetchAdditionalInfoRespEntity> additionalInfoList = new ArrayList<BillFetchAdditionalInfoRespEntity>();
//	@Embedded
//	private BillFetchBillerResponseRespEntity billerResponse;
}
