package com.anniyamtech.bbps.responseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

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


public class BillFetchAdditionalInfoRespEntity  {

	private long id;
	@Column(name="tag_name")
	private String name;
		@Column(name="tag_value")
	private String value;
}
