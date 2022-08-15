package com.anniyamtech.bbps.responseEntity;

import java.io.Serializable;
import java.util.Date;

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

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "ts" }, allowSetters = true)
@Table(name = "biller_details_resp_entity")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsRespEntity implements Serializable {
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
	private Date ts;
	@Column(name = "OrigInst")
	private String origInst;
	@Column(name = "responseCode")
	private String responseCode;
	@Column(name = "responseReason")
	@Size(min = 7, max = 20)
	private String responseReason;
	@Column(name = "complianceRespCd", length = 6)
	private String complianceRespCd;
	@Column(name = "complianceReason")
	@Size(min = 7, max = 20)
	private String complianceReason;

}
