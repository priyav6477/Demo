package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.responseEntity.TransactionStatusResponse1;

@Repository
public interface TransactionStatusResponseRepository extends JpaRepository<TransactionStatusResponse1, String>{

}
