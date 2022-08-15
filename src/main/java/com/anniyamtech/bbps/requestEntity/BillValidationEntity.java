package com.anniyamtech.bbps.requestEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bill_validation_req")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "ts" }, allowSetters = true)
@Setter
@Getter
public class BillValidationEntity implements Serializable {
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

	@Column(name = "agent_id")
	private String agentId;
	@Embedded
	private BillValidationBillDetails billDetails;

}
