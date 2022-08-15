package com.anniyamtech.bbps.requestEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bill_fetch_req")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Setter
@Getter
public class BillFetchEntity implements Serializable {

	@Column(name = "ver", length = 3)
	@NotBlank
	private String ver;
	@Column(name = "ts")
	private String ts;

	@Column(name = "origInst", length = 4)
	@NotBlank
	private String origInst;
	@Id
	@Column(name = "refid", length = 35)
	@NotBlank
	private String refId;
	@Column(name = "msgId", length = 35)
	@NotBlank
	private String msgId;
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_fetch_analytics_tag", joinColumns = @JoinColumn(name = "refid"))
	private List<BillFetchAnalyticsTag> analytics = new ArrayList<BillFetchAnalyticsTag>();
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_fetch_risk_scores", joinColumns = @JoinColumn(name = "refid"))
	private List<BillFetchRiskScoresTag> riskScores = new ArrayList<BillFetchRiskScoresTag>();
	@Embedded
	private BillFetchAgent agent;
	@Embedded
	private BillFetchBillDetails billDetails;
	@Embedded
	private BillFetchCustomerDetailsTag customer;

}
