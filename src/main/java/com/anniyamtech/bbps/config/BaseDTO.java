package com.anniyamtech.bbps.config;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO implements Serializable {

	private static final long  	 serialVersionUID = 1L;

	private Integer statusCode; // 00   //.k3.
	private String message; //.k2.
	private Object Data;
	
	public BaseDTO() {

	}

	public BaseDTO(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}