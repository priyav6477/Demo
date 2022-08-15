package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.responseEntity.BillerListResponse;

@Repository
public interface BillerListResponseRepository extends JpaRepository<BillerListResponse, String>{

}
