package com.anniyamtech.bbps.requestEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
@Table(name = "bill_payment_request")
@Data
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)

@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "ts" }, allowSetters = true)
public class BillPaymentReqEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ver", length = 3)
	@NotBlank
	private String ver;

	@Column(name = "origInst", length = 4)
	@NotBlank
	private String origInst;

	@Id
	@Column(name = "refId", length = 35)
	@NotBlank
	private String refId;

	@Column(name = "msgId", length = 35)
	@NotBlank
	private String msgId;

	@Column(name = "ts")
	// @Temporal(TemporalType.TIMESTAMP)
	// @CreatedDate
	private String ts;

	@Column(name = "txnReferenceId", length = 20)
	private String txnReferenceId;

	@Column(name = "type")
	@NotBlank
	private String type;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_payment_risk_scores_request", joinColumns = @JoinColumn(name = "refId"))
	private List<BillPaymentRiskScores> riskScores = new ArrayList<BillPaymentRiskScores>();

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_payment_analytics_request", joinColumns = @JoinColumn(name = "refId"))
	private List<BillPaymentAnalytics> analytics = new ArrayList<BillPaymentAnalytics>();

	@Embedded
	private BillPaymentCustomer customer;

	@Embedded
	private BillPaymentAgent agent;

	@Embedded
	private BillPaymentBillDetails billDetails;

	@Column(name = "amount", length = 20)
	@NotBlank
	private String amount;

	@Column(name = "custConvFee", length = 20)
	private String custConvFee;

	@Column(name = "couCustConvFee", length = 20)
	private String couCustConvFee;

	@Column(name = "currency", length = 3)
	@NotBlank
	private String currency;

	@Column(name = "splitPayAmount", length = 20)
	
	private String splitPayAmount;

	@Column(name = "tags")
	
	private String tags;

	@Column(name = "quickPay", length = 3)
	@NotBlank
	private String quickPay;

	@Column(name = "splitPay", length = 3)
	@NotBlank
	private String splitPay;

	@Column(name = "offusPay", length = 3)
	@NotBlank
	private String offusPay;

	@Column(name = "paymentMode", length = 20)
	@NotBlank
	private String paymentMode;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_payment_payment_information_request", joinColumns = @JoinColumn(name = "refId"))
	private List<BillPaymentPaymentInformation> paymentInformation = new ArrayList<BillPaymentPaymentInformation>();

	@Column(name = "debitAccountNo", length = 20)
	private String debitAccountNo;

	

}
