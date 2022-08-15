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
@Table(name = "biller_list_billers_resp")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SQLDelete(sql = "UPDATE biller_list_response SET active = false WHERE billerId=?")
@Where(clause = "active=true")
@EntityListeners(AuditingEntityListener.class)
public class BillersResponse {

	@Id
	@Column(name = "billerId")
	@NotBlank
	private String billerId;
	@Column(name = "billerName")
	private String billerName;
	@Column(name = "billerAliasName")
	private String billerAliasName;
	@Column(name = "billerCategoryName")
	private String billerCategoryName;
	@Column(name = "Status")
	private String Status;
	@Column(name = "lastUpdated")
	private Date lastUpdated;
	
	@ManyToOne
	@JoinColumn (name = "ref_id", referencedColumnName="ref_id") 
	private BillerListResponse biller;
	                                                   
	private boolean active = Boolean.TRUE;

	

}
