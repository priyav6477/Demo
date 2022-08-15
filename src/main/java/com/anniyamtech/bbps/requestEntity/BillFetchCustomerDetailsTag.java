package com.anniyamtech.bbps.requestEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Setter
@Getter
@Embeddable
public class BillFetchCustomerDetailsTag {
	@NotBlank
	@Column(name="mobile")
	private String mobile;
	@ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "bill_fetch_customer_tags", joinColumns = @JoinColumn(name = "refid"))
	private List<BillFetchCustomerTags> tags = new ArrayList<BillFetchCustomerTags>();
	
	
}
