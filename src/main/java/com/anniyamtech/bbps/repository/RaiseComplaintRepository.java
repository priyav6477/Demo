package com.anniyamtech.bbps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anniyamtech.bbps.requestEntity.RaiseComplaintRequestEntity;
@Repository
public interface RaiseComplaintRepository  extends JpaRepository<RaiseComplaintRequestEntity, String>  {

}
