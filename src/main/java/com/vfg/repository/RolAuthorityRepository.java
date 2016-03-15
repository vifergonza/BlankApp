package com.vfg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolAuthorityRepository extends CrudRepository<RolAuthority, Long>{

	
}
