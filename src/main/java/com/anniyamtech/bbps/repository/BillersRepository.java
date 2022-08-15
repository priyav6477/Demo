package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.responseEntity.BillersResponse;

@Repository
public interface BillersRepository extends JpaRepository<BillersResponse, String>{

}
