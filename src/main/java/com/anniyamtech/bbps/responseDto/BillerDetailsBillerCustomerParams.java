package com.anniyamtech.bbps.responseDto;

import javax.persistence.Embeddable;

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
public class BillerDetailsBillerCustomerParams {
private String paramName;
private String dataType;
private String optional;
private int minLength;
private int maxLength;
private String regex;
}
