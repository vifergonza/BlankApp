package com.vfg.services;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vfg.repository.entities.User;

/**
 * <p> Servicios disponibles sobre los usuarios.</p>
 * <p> Extiende UserDetailService para poder ser usada por Spring Security.</p>
 *
 * @author vifergo
 * @since v0.1
 */
public interface UserServiceI extends UserDetailsService {

    /**
     * Retorna el usuario cuyo username recibe como parametro. Es usada por
     * Spring Security para acceder al usuario autenticado.
     *
     * @param username nombre de usuario
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException;

    /**
     * Recupera todos los usuarios de la base de datos.
     *
     * @return Collection<User>
     */
    Collection<User> findUsers();

    /**
     * Cremos una serie de usuarios para cargarlos al arranque de la aplicacion.
     * Util para desarrollo.
     */
    void initDatabase();

}
