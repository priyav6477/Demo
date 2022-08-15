package com.anniyamtech.bbps.config;

import lombok.Data;

@Data
public class ResponseClass2 {

	private String message;
	private Object Data;

	public ResponseClass2() {
		super();
	}

	public ResponseClass2(String message) {
		super();
		this.message = message;
	}

	public ResponseClass2(String message, Object data) {
		super();
		this.message = message;
		Data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}

}
