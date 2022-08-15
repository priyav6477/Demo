package com.anniyamtech.bbps.config;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	/** e@Datarror code. 0 if success else unique error code value */

	private String status;  // s and f
	private Integer errorCode;  // 00 
	private String userDisplayMesg;  //User customMeaasge
	private Object responseContent;
	
	private Integer statusCode;  // 00 
	private String message; 
	private Object data;
	
	
/*	private String status;  // s and f
	private Integer errorCode;  // 00 
	private String errorDescription;  //success /failure
	private String userDisplayMesg;  //User customMeaasge
	private Object responseContent;*/
	
	
	
}