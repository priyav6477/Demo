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
import lombok.Getter;
import lombok.Setter;

@Entity
@SQLDelete(sql = "UPDATE biller_details SET active=false WHERE id=?")
@Where(clause = "active=true")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "ts" }, allowSetters = true)
@Table(name = "biller_details_req")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Setter
@Getter
public class BillerDetailsEntity implements Serializable{
	private static final long serialVersionUID=1L;
	@Column(name = "ver",length=3)
	@NotBlank
	private String ver;

	@Column(name = "origInst",length=4)
	@NotBlank
	private String origInst;
	
	@Column(name = "refId",length=35)
	@NotBlank
	private String refId;
	@Column(name = "ts")
	private String ts;
	
	@Id
	@Column(name = "biller_id",length=14)
	private String billerId;
	
	private Boolean active=Boolean.TRUE;
	
}
	
	