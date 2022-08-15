package com.anniyamtech.bbps.responseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
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
@Table(name = "biller_validation_response")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class BillValidationResponseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "version")
	private String ver;
	@Column(name = "ts")
	private String ts;
	@Column(name = "OrigInst")
	private String origInst;
	@Id
	@Column(name = "refid")
	private String refId;
	@Column(name = "approvalRefNum", length = 35)
	private String approvalRefNum;
	@Column(name = "responseCode")
	private String responseCode;
	@Column(name = "responseReason")
	@Size(min = 7, max = 20)
	private String responseReason;
	@Column(name = "complianceRespCd")
	private String complianceRespCd;
	@Column(name = "complianceReason")
	//@Size(min = 7, max = 20)
	private String complianceReason;

//	private List<String> additionalInfo;

}
