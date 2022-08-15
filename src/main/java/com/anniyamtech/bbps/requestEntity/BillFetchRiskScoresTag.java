package com.anniyamtech.bbps.requestEntity;

import java.util.Date;

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
public class BillFetchRiskScoresTag {
	@NotBlank
	@Column(name = "provider")
	private String provider;
	@NotBlank
	@Column(name = "type")
	private String type;
	@NotBlank
	@Column(name = "value")
	private String value;


}
