package com.vfg.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vfg.repository.entities.RolAuthority;

/**
 * Repositorio de acceso a datos sobre roles.
 *
 * @author vifergo
 * @since v0.1
 */
@Repository
public interface RolAuthorityRepository extends CrudRepository<RolAuthority, Long> {

}
