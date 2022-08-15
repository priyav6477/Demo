package com.anniyamtech.bbps.requestEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "transaction_status_using_mobile")
@Data
@EqualsAndHashCode(callSuper = false)
@SQLDelete(sql = "UPDATE transaction_status_using_mobile SET active = false WHERE refId=?")
@Where(clause = "active=true")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "ts" }, allowSetters = true)
public class TransactionStatus2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "refId", length = 35)
	@NotBlank
	private String refId;

	@Column(name = "ver", length = 3)
	@NotBlank
	private String ver;

	@Column(name = "origInst", length = 4)
	@NotBlank
	private String origInst;

	@Column(name = "ts")
	private String ts;

	@Column(name = "msgId", length = 35)
	@NotBlank
	private String msgId;

	@Column(name = "xchangeId", length = 3)
	@NotBlank
	private String xchangeId;

	@Column(name = "mobile", length = 10)
	@NotBlank
	private String mobile;

	@Column(name = "fromDate", length = 10)
	@NotBlank
	private String fromDate;

	@Column(name = "toDate", length = 10)
	@NotBlank
	private String toDate;

	@Column(name = "otp", length = 10, nullable = true)
	private String otp;

	private boolean active = Boolean.TRUE;
}
