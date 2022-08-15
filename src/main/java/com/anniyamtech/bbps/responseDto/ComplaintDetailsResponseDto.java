package com.anniyamtech.bbps.responseDto;

import javax.persistence.Column;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ComplaintDetailsResponseDto {
	@Column(nullable=true)
	private String assigned;
	@Column(nullable=true)
	private String complaintId;
	@Column(nullable=true)
	private String complaintStatus;
	@Column(nullable=true)
	private String remarks;

}