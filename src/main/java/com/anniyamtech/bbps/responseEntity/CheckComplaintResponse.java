package com.anniyamtech.bbps.responseEntity;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "check_complaint_response")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SQLDelete(sql = "UPDATE biller_list_req SET active = false WHERE refId=?")
@Where(clause = "active=true")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "ts" }, allowSetters = true)
public class CheckComplaintResponse implements Serializable {

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
	private Date ts;

	@Column(name = "responseCode", length = 3)
	private String responseCode;

	@Column(name = "responseReason", length = 50)
	private String responseReason;

	@Column(name = "msgId", length = 35)
	private String msgId;

	@Column(name = "xchangeId", length = 3)
	private String xchangeId;

	@Column(name = "assigned", length = 30)
	private String assigned;

	@Column(name = "complaintId", length = 20)
	private String complaintId;

	@Column(name = "complaintStatus", length = 20)
	private String complaintStatus;

	@Column(name = "remarks", length = 100)
	private String remarks;

	@Column(name = "complianceRespCd")
	private String complianceRespCd;

	@Column(name = "complianceReason")
	private String complianceReason;

	private boolean active = Boolean.TRUE;

}
