package com.vfg.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBlankRepository extends CrudRepository<UserBlank, Long>{

	public Collection<UserBlank> findByUsername(String username);
	
}