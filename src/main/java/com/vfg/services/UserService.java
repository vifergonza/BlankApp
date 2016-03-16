package com.vfg.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vfg.repository.RolAuthority;
import com.vfg.repository.RolAuthorityRepository;
import com.vfg.repository.User;
import com.vfg.repository.UserRepository;

/**
 * <p>Servicios disponibles sobre los usuarios</p>
 * <p>Implementa UserDetailService para poder ser usada por Spring Security</p>
 * 
 * @author vifergo
 * @since v0.1
 */
@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userBlankRepository;

	@Autowired 
	private RolAuthorityRepository rolAuthorityRepository;

	/**
	 * Retorna el usuario cuyo username recibe como parametro.
	 * Es usada por Spring Security para acceder al usuario autenticado.
	 * 
	 * @param username nombre de usuario
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 */
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<User> usuarios = userBlankRepository.findByUsername(username);
		if (!CollectionUtils.isEmpty(usuarios) && 1==usuarios.size()) {
			return (UserDetails) usuarios.toArray()[0];
		} else {
			throw new UsernameNotFoundException(username);
		}
    }
	
	/**
	 * Recupera todos los usuarios de la base de datos
	 * 
	 * @return Collection<User>
	 */
	public Collection<User> findUsers() {
		Iterator<User> iterator = userBlankRepository.findAll().iterator();
		Collection<User> usuarios = new ArrayList<User>();
		User item = null;
		while (iterator.hasNext()) {
			item=iterator.next();
			item.setPassword(null);
			usuarios.add(item);
		}
		return usuarios;
	}

	/**
	 * Cremos una serie de usuarios para cargarlos al arranque de la aplicacion.
	 * Util para desarrollo.
	 */
	public void initDatabase() {
		RolAuthority user = new RolAuthority("USER", "Usuario normal", Long.valueOf(0));
		RolAuthority editor = new RolAuthority("EDITOR", "Usuario editor", Long.valueOf(50));
		RolAuthority admin = new RolAuthority("ADMIN", "Usuario administrador", Long.valueOf(90));
		RolAuthority root = new RolAuthority("ROOT", "Modo dios", Long.valueOf(100));
		
		user = rolAuthorityRepository.save(user);
		editor = rolAuthorityRepository.save(editor);
		admin = rolAuthorityRepository.save(admin);
		root = rolAuthorityRepository.save(root);
		
		Collection<RolAuthority> rolesUsuario01 = new ArrayList<RolAuthority>();
		rolesUsuario01.add(root);
		rolesUsuario01.add(admin);
		User usuario01 = new User("vfg", "vfg", rolesUsuario01);
		usuario01.setAccountNonExpired(Boolean.TRUE);
		usuario01.setAccountNonLocked(Boolean.TRUE);
		usuario01.setEnabled(Boolean.TRUE);
		usuario01.setCredentialsNonExpired(Boolean.TRUE);
		usuario01 = userBlankRepository.save(usuario01);
		
		Collection<RolAuthority> rolesUsuario02 = new ArrayList<RolAuthority>();
		rolesUsuario02.add(user);
		User usuario02 = new User("user", "user", rolesUsuario02);
		usuario02.setAccountNonLocked(Boolean.TRUE);
		usuario02.setEnabled(Boolean.TRUE);
		usuario02.setCredentialsNonExpired(Boolean.TRUE);
		usuario02 = userBlankRepository.save(usuario02);
		
		Collection<RolAuthority> rolesUsuario03 = new ArrayList<RolAuthority>();
		rolesUsuario03.add(user);
		rolesUsuario03.add(editor);
		User usuario03 = new User("editor", "editor", rolesUsuario03);
		usuario03.setEnabled(Boolean.TRUE);
		usuario03.setCredentialsNonExpired(Boolean.TRUE);
		usuario03 = userBlankRepository.save(usuario03);

		Collection<RolAuthority> rolesUsuario04 = new ArrayList<RolAuthority>();
		rolesUsuario04.add(user);
		User usuario04 = new User("user2", "user2", rolesUsuario02);
		usuario04 = userBlankRepository.save(usuario04);
		
		User usuario05 = new User("user3", "user3", rolesUsuario02);
		usuario05.setAccountNonExpired(Boolean.TRUE);
		usuario05.setAccountNonLocked(Boolean.TRUE);
		usuario05.setEnabled(Boolean.TRUE);
		usuario05.setCredentialsNonExpired(Boolean.TRUE);
		usuario05 = userBlankRepository.save(usuario05);
		
	}
	
}
