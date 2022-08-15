package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.responseEntity.BillValidationResponseEntity;

@Repository
public interface BillValidationRespRepository extends JpaRepository<BillValidationResponseEntity, String> {

}
