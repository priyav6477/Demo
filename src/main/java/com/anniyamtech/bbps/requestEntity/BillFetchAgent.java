package com.anniyamtech.bbps.requestEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

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
public class BillFetchAgent {
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "bill_fetch_device_tag", joinColumns = @JoinColumn(name = "refid"))
	private List<BillFetchDeviceTag> device = new ArrayList<BillFetchDeviceTag>();
	@Column(name = "agent_id")
	private String id;

}
