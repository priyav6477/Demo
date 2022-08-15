package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.responseEntity.BillPaymentRespEntity;

@Repository
public interface BillPaymentRespRepository extends JpaRepository<BillPaymentRespEntity, String> {
	BillPaymentRespEntity findBytxnReferenceId(String txnReferenceId);

}
