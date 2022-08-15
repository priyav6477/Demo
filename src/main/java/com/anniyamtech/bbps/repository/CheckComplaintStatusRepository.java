package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.requestEntity.CheckComplaintReq;

@Repository
public interface CheckComplaintStatusRepository extends JpaRepository<CheckComplaintReq, String>{

}
