package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.requestEntity.BillerListReq;

@Repository
public interface BillerListRepository extends JpaRepository<BillerListReq, String>{

}
