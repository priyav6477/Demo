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
public class BillFetchDeviceTag {
	@Column(name="device_name")
	@NotBlank
	private String name;
	@Column(name="device_value")
	@NotBlank
	private String value;
	
}
