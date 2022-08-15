package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anniyamtech.bbps.requestEntity.BillValidationEntity;

public interface BillValidationRepository extends JpaRepository<BillValidationEntity, String> {

}
