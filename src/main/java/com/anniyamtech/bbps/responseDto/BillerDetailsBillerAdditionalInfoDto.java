package com.anniyamtech.bbps.responseDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillerDetailsBillerAdditionalInfoDto {
	private String paramName;
	private String dataType;
	private String optional;
	private int minLength;
	private int maxLength;
	private String regex;
}
