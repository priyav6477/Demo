package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.responseEntity.TxnListResponse;

@Repository
public interface TxnListRespository extends JpaRepository<TxnListResponse, String>{

}
