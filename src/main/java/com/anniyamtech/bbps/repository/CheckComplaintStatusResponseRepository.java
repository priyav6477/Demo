package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.responseEntity.CheckComplaintResponse;

@Repository
public interface CheckComplaintStatusResponseRepository extends JpaRepository<CheckComplaintResponse, String>{

}
