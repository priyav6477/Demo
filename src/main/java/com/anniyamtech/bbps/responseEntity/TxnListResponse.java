package com.anniyamtech.bbps.responseEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "txn_list")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SQLDelete(sql = "UPDATE biller_list_response SET active = false WHERE billerId=?")
@Where(clause = "active=true")
@EntityListeners(AuditingEntityListener.class)
public class TxnListResponse {

	@Id
	@Column(name = "txnReferenceId")
	@NotBlank
	private String txnReferenceId;

	@Column(name = "agentId")
	private String agentId;

	@Column(name = "billerId")
	private String billerId;

	@Column(name = "amount")
	private String amount;

	@Column(name = "txnDate")
	private Date txnDate;

	@Column(name = "txnStatus")
	private String txnStatus;

	@ManyToOne
	@JoinColumn(name = "fk_refId", referencedColumnName = "refId")
	private TransactionStatusResponse1 response1;

	private boolean active = Boolean.TRUE;

}
