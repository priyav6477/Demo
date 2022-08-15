package com.anniyamtech.bbps.responseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "raise_complaint_response")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Setter
@Getter
public class RaiseComplaintResponseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "ver", length = 3)
	//@NotBlank
	private String ver;
	@Column(name = "ts")
	//@Temporal(TemporalType.TIMESTAMP)
	//@CreatedDate
	private String ts;

	@Column(name = "origInst")
	@NotBlank
	private String origInst;
	@Id
	@Column(name = "refid")
	@NotBlank
	private String refId;
	@Column(name = "responseCode", length = 15)
	private String responseCode;
	@Column(name = "responseReason")
	//@Size(min = 7, max = 20)
	private String responseReason;
	@Column(name = "msgId", length = 35)
	@NotBlank
	private String msgId;
	@Column(name = "xchangeId", length = 3)
	//@NotBlank
	private String xchangeId;
	@Column(name = "assigned")
	private String assigned;
	@Column(name = "complaintId")
	private String complaintId;
	@Column(name = "complianceRespCd")
	private String complianceRespCd;

	@Column(name = "complianceReason")
	private String complianceReason;
	
	
}
