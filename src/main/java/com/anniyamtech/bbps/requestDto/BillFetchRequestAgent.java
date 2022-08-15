package com.anniyamtech.bbps.requestDto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillFetchRequestAgent {
	private List<BillFetchRequestDeviceTag> device=new ArrayList<BillFetchRequestDeviceTag>();
	private String id;
}
