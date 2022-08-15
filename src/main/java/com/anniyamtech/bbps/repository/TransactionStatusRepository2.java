package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.requestEntity.TransactionStatus2;

@Repository
public interface TransactionStatusRepository2 extends JpaRepository<TransactionStatus2, String>{

}
