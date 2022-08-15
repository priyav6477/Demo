package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.requestEntity.BillFetch;
@Repository
public interface BillFetchIdRepository extends JpaRepository<BillFetch, Integer>  {

}
