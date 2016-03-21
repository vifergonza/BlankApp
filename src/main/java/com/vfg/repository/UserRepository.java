package com.vfg.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vfg.repository.entities.User;

/**
 * Repositorio de acceso a datos sobre usuarios.
 *
 * @author vifergo
 * @since v0.1
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Busca usuario por el valor de username.
     *
     * @param username a buscar en los usuarios.
     * @return Colecion de usuarios con ese username.
     */
    Collection<User> findByUsername(String username);

}
