package com.anniyamtech.bbps.responseEntity;

import javax.persistence.Column;
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
public class BillFetchBillerResponseTagsEntity {
	@Column(name = "tag_name")
	private String name;
	@Column(name = "tag_value")
	private String value;
}
