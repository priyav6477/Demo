package com.anniyamtech.bbps.responseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.validation.constraints.NotBlank;

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
@Table(name = "bill_payment_response")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter

public class BillPaymentRespEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "refId")
	@NotBlank
	private String refId;
	@Column(name = "origInst", length = 4)
	@NotBlank
	private String origInst;
	@Column(name = "ts")
	private String ts;
	@Column(name = "ver", length = 3)
	@NotBlank
	private String ver;
	@Column(name = "responseCode")
	private String responseCode;
	@Column(name = "responseReason")
	@NotBlank
	private String responseReason;
	@Column(name = "complianceRespCd")
	private String complianceRespCd;
	@Column(name = "complianceReason")
	private String complianceReason;
	@Column(name = "approvalRefNum")
	private String approvalRefNum;
	@Column(name = "msgId", length = 35)
	@NotBlank
	private String msgId;
	@Column(name = "txnReferenceId", length = 20)
	@NotBlank
	private String txnReferenceId;
	private String billerId;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_payment_customer_params_response", joinColumns = @JoinColumn(name = "refId"))
	private List<BillPaymentCustomerParamsResponse> customerParams = new ArrayList<BillPaymentCustomerParamsResponse>();
	private String customerName;
	private String amount;
	private Date dueDate;
	private Date billDate;
	private String billNumber;
	private String billPeriod;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_payment_tags_response", joinColumns = @JoinColumn(name = "refId"))
	private List<BillPaymentTagsResponse> tags = new ArrayList<BillPaymentTagsResponse>();
}