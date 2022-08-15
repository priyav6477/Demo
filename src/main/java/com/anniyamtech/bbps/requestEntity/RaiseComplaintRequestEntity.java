
package com.anniyamtech.bbps.requestEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
  
  @SQLDelete(sql = "UPDATE raise_complaint_request SET active=false WHERE id=?")
  
  @Where(clause = "active=true")
  
  @EntityListeners(AuditingEntityListener.class)
  
  @JsonIgnoreProperties(value = { "ts" }, allowSetters = true)
  
  @Table(name = "raise_complaint_request")
  
  @Data
  
  @EqualsAndHashCode(callSuper = false)
  
  @JsonInclude(JsonInclude.Include.NON_NULL)
  
  @Setter
  
  @Getter 
  public class RaiseComplaintRequestEntity implements Serializable { 
	@Id
	@Column(name = "refId", length = 35)
	@NotBlank
	private String refId;
	@Column(name = "ts")
	private String ts;
	@Column(name = "origInst", length = 4)
	@NotBlank
	private String origInst;
	@Column(name = "ver", length = 3)
	@NotBlank
	private String ver;
	@Column(name = "msgId", length = 35)
	@NotBlank
	private String msgId;
	@Column(name = "xchange_Id", length = 3)
	@NotBlank
	private String xchangeId;
	@Column(name = "complaint_Type")
	@NotBlank
	private String complaintType;
	@Column(name = "participation_Type")
	//@NotBlank
	private String participationType;
	@Column(name = "agent_Id")
//	@NotBlank
	private String agentId;
	private String billerId;
	@Column(name = "serv_Reason")
//	@NotBlank
	private String servReason;
	
	@Column(name = "disposition")
//	@NotBlank
	private String disposition;
	@Column(name = "txn_referenceid")
//	@NotBlank
	private String txnReferenceId;
	@Column(name = "description")
	//@NotBlank
	private String description;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "otp")
	private String otp;
	private Boolean active = Boolean.TRUE;
}