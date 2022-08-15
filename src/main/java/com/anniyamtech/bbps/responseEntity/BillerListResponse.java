package com.anniyamtech.bbps.responseEntity;

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

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "biller_list_response")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SQLDelete(sql = "UPDATE biller_list_response SET active = false WHERE refId=?")
@Where(clause = "active=true")
@EntityListeners(AuditingEntityListener.class)
public class BillerListResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ref_id",length = 35)
	@NotBlank
	private String refId;

	@Column(name = "ver",length = 3)
	@NotBlank
	private String ver;

	@Column(name = "origInst",length = 4)
	@NotBlank
	private String origInst;

	@Column(name = "ts")
	private String ts;
	
	@Column(name = "responseCode")
	private String responseCode;
	
	@Column(name = "responseReason")
	private String responseReason;

	@Column(name = "complianceRespCd")
	private String complianceRespCd;

	@Column(name = "complianceReason")
	private String complianceReason;

	
	private boolean active = Boolean.TRUE;

}
  