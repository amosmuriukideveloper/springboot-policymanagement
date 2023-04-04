package com.microS.policymanagement.Repository;

import com.microS.policymanagement.models.PolicyHolder;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PolicyHolderRepository  extends JpaRepository<PolicyHolder, Long> {

}
