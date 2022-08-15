package com.anniyamtech.bbps.responseDto;



import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillersDto {
	private String billerId;
	private String billerName;
	private String billerAliasName;
	private String billerCategoryName;
	private String status;
	private Date lastUpdated;
			
	
}
