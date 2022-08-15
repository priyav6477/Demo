package com.anniyamtech.bbps.requestEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
public class BillFetchAnalyticsTag {
	@Column(name = "analytics_name")
	@NotBlank
	private String name;
	@Column(name = "analytics_value")
	private String value;

	
	
}
