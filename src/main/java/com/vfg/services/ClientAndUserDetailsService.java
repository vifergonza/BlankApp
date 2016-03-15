package com.vfg.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

/**
 * <p>Unificamos en una sola clase los servicios que nos permitiran acceder a los detalles
 * del usuario y del cliente que realiza peticiones en nombre del usuario.</p>
 * 
 * @author vifergo
 * @since v0.1
 */
public class ClientAndUserDetailsService implements UserDetailsService, ClientDetailsService {

	private final ClientDetailsService clients_;

	private final UserDetailsService users_;
	
	private final ClientDetailsUserDetailsService clientDetailsWrapper_;

	public ClientAndUserDetailsService(ClientDetailsService clients, UserDetailsService users) {
		super();
		clients_ = clients;
		users_ = users;
		clientDetailsWrapper_ = new ClientDetailsUserDetailsService(clients_);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clients_.loadClientByClientId(clientId);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		try{
			user = users_.loadUserByUsername(username);
		}catch(UsernameNotFoundException e){
			user = clientDetailsWrapper_.loadUserByUsername(username);
		}
		return user;
	}

}
