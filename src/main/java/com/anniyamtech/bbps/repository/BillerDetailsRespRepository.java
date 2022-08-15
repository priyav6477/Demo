package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.responseEntity.BillerDetailsRespEntity;

@Repository
public interface BillerDetailsRespRepository extends JpaRepository<BillerDetailsRespEntity,String> {

}
