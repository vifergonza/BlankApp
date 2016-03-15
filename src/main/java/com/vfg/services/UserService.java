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
import com.vfg.repository.UserBlank;
import com.vfg.repository.UserBlankRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserBlankRepository userBlankRepository;

	@Autowired 
	private RolAuthorityRepository rolAuthorityRepository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<UserBlank> usuarios = userBlankRepository.findByUsername(username);
		if (!CollectionUtils.isEmpty(usuarios) && 1==usuarios.size()) {
			return (UserDetails) usuarios.toArray()[0];
		} else {
			throw new UsernameNotFoundException(username);
		}
    }
	
	public Collection<UserBlank> findUsers() {
		Iterator<UserBlank> iterator = userBlankRepository.findAll().iterator();
		Collection<UserBlank> usuarios = new ArrayList<UserBlank>();
		UserBlank item = null;
		while (iterator.hasNext()) {
			item=iterator.next();
			item.setPassword(null);
			usuarios.add(item);
		}
		return usuarios;
	}

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
		UserBlank usuario01 = new UserBlank("vfg", "vfg", rolesUsuario01);
		usuario01 = userBlankRepository.save(usuario01);
		
		Collection<RolAuthority> rolesUsuario02 = new ArrayList<RolAuthority>();
		rolesUsuario02.add(user);
		UserBlank usuario02 = new UserBlank("user", "user", rolesUsuario02);
		usuario02 = userBlankRepository.save(usuario02);
		
		Collection<RolAuthority> rolesUsuario03 = new ArrayList<RolAuthority>();
		rolesUsuario03.add(user);
		rolesUsuario03.add(editor);
		UserBlank usuario03 = new UserBlank("editor", "editor", rolesUsuario03);
		usuario03 = userBlankRepository.save(usuario03);

	}
	
}
