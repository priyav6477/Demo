package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.requestEntity.BillFetchEntity;
@Repository
public interface BillFetchRepository extends JpaRepository<BillFetchEntity, String>  {

}
