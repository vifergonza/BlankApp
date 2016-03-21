package com.vfg.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vfg.repository.RolAuthorityRepository;
import com.vfg.repository.UserRepository;
import com.vfg.repository.entities.RolAuthority;
import com.vfg.repository.entities.User;
import com.vfg.security.CustomPasswordEncoder;
import com.vfg.services.UserServiceI;

/**
 * <p> Servicios disponibles sobre los usuarios.</p>
 *
 * @author vifergo
 * @since v0.1
 */
@Service
public class UserServiceImpl implements UserServiceI {

    /**
     * Repositorio de usuario.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Repositorio de roles.
     */
    @Autowired
    private RolAuthorityRepository rolAuthorityRepository;

    /**
     * Componenente para encriptar las contraseñas.
     */
    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public final UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Collection<User> usuarios = userRepository.findByUsername(username);
        if (!CollectionUtils.isEmpty(usuarios) && 1 == usuarios.size()) {
            return (UserDetails) usuarios.toArray()[0];
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<User> findUsers() {
        Iterator<User> iterator = userRepository.findAll().iterator();
        Collection<User> usuarios = new ArrayList<User>();
        while (iterator.hasNext()) {
            usuarios.add(iterator.next());
        }
        return usuarios;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void initDatabase() {
        RolAuthority user = new RolAuthority("USER", "Usuario normal", RolAuthority.PRIORITY_MIN);
        RolAuthority editor = new RolAuthority("EDITOR", "Usuario editor", RolAuthority.PRIORITY_50);
        RolAuthority admin = new RolAuthority("ADMIN", "Usuario administrador", RolAuthority.PRIORITY_90);
        RolAuthority root = new RolAuthority("ROOT", "Modo dios", RolAuthority.PRIORITY_MAX);

        user = rolAuthorityRepository.save(user);
        editor = rolAuthorityRepository.save(editor);
        admin = rolAuthorityRepository.save(admin);
        root = rolAuthorityRepository.save(root);

        final Collection<RolAuthority> rolesUsuario01 = new ArrayList<RolAuthority>();
        rolesUsuario01.add(root);
        rolesUsuario01.add(admin);
        User usuario01 = new User("vfg", "vfg", rolesUsuario01);
        usuario01.setAccountNonExpired(Boolean.TRUE);
        usuario01.setAccountNonLocked(Boolean.TRUE);
        usuario01.setEnabled(Boolean.TRUE);
        usuario01.setCredentialsNonExpired(Boolean.TRUE);
        usuario01.setPassword(customPasswordEncoder.encode(usuario01.getPassword()));
        usuario01 = userRepository.save(usuario01);

        final Collection<RolAuthority> rolesUsuario02 = new ArrayList<RolAuthority>();
        rolesUsuario02.add(user);
        User usuario02 = new User("user", "user", rolesUsuario02);
        usuario02.setAccountNonLocked(Boolean.TRUE);
        usuario02.setEnabled(Boolean.TRUE);
        usuario02.setCredentialsNonExpired(Boolean.TRUE);
        usuario02.setPassword(customPasswordEncoder.encode(usuario02.getPassword()));
        usuario02 = userRepository.save(usuario02);

        final Collection<RolAuthority> rolesUsuario03 = new ArrayList<RolAuthority>();
        rolesUsuario03.add(user);
        rolesUsuario03.add(editor);
        User usuario03 = new User("editor", "editor", rolesUsuario03);
        usuario03.setEnabled(Boolean.TRUE);
        usuario03.setCredentialsNonExpired(Boolean.TRUE);
        usuario03.setPassword(customPasswordEncoder.encode(usuario03.getPassword()));
        usuario03 = userRepository.save(usuario03);

        final Collection<RolAuthority> rolesUsuario04 = new ArrayList<RolAuthority>();
        rolesUsuario04.add(user);
        User usuario04 = new User("user2", "user2", rolesUsuario02);
        usuario04.setPassword(customPasswordEncoder.encode(usuario04.getPassword()));
        usuario04 = userRepository.save(usuario04);

        User usuario05 = new User("user3", "user3", rolesUsuario02);
        usuario05.setAccountNonExpired(Boolean.TRUE);
        usuario05.setAccountNonLocked(Boolean.TRUE);
        usuario05.setEnabled(Boolean.TRUE);
        usuario05.setCredentialsNonExpired(Boolean.TRUE);
        usuario05.setPassword(customPasswordEncoder.encode(usuario05.getPassword()));
        usuario05 = userRepository.save(usuario05);

    }

}
