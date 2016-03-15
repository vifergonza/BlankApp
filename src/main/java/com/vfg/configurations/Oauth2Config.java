package com.vfg.configurations;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.vfg.repository.User;
import com.vfg.services.ClientAndUserDetailsService;
import com.vfg.services.UserService;

@Configuration
public class Oauth2Config {

	/**
	 * <p>Configuramos Spring Security para que use el UserDetailsService que 
	 * crearemos mas adelante.</p>
	 * 	
	 * @author vifergo
	 * @since v0.1
	 *
	 */
	@Configuration
	@EnableWebSecurity
	protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private UserDetailsService userDetailsService;

	    @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
		
		@Override
		protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);
		}
		
	}

	/**
	 * <p>Configuramos la aplicacion como servidor de recursos.</p>
	 * 
	 * <p>Desde aqui configuraremos, entre otras cosas, los permisos de acceso a los recursos
	 * en funcion de las rutas. Dado que tratamos de construir un servicio REST deberemos
	 * prestar atencion especial a los permisos que damos a metodos como POST, PUT y DELETE</p> 	
	 * 
	 * @author vifergo
	 * @since v0.1
	 *
	 */
	@Configuration
	@EnableResourceServer
	protected static class ResourceServer extends ResourceServerConfigurerAdapter {


		@Override
		public void configure(HttpSecurity http) throws Exception {
			// CSRF es un token de proteccion para accesos web.
			// Habra que activarlo si queremos usar terceros como autenticadores
			// (facebook, twitter, google)
			http.csrf().disable();

			// Permitimos acceso anonimo a la solicitud de tokens
			http.authorizeRequests().antMatchers("/oauth/token").anonymous();
			
			// Permitimos acceso libre al recurso "about" y todos sus descendientes
			http.authorizeRequests().antMatchers("/about/**").permitAll();
			
			//autorizamos todos los get de momento
			http.authorizeRequests().antMatchers(HttpMethod.GET, "/**").anonymous();

			
			// Exigimos que cualquier peticion get tenga el scope 'read'
//			http.authorizeRequests().antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')");

			// Exigimos que cualquier peticion que no encaje con los parametros anteriores 
			//tenga el scope write
//			http.authorizeRequests().antMatchers("/**").access("#oauth2.hasScope('write')");

		}

		/**
		 * <p>Configuramos la aplicacion con servidor de autenticacion Oauth2</p>
		 * 
		 * @author vifergo
		 * @since v0.1
		 */
		@Configuration
		@EnableAuthorizationServer
		@Order(Ordered.LOWEST_PRECEDENCE - 100)
		protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

			@Autowired
			@Qualifier("authenticationManagerBean")
			private AuthenticationManager authenticationManager;
			
			@Autowired
			private UserService userService;
			
			/**
			 * Servidor de detalles de usuario y de cliente por el que se conecta el usuario.
			 * 
			 * TODO Separar en dos clases independientes
			 */
			private ClientAndUserDetailsService clientUserDetailService;

			/**
			 * <p> Incializamos OAuth2 </p>
			 * <p> Como punto de partida harcodeamos los usuarios y los clientes, 
			 * pero esto no deberia hacerse asi jamas.<p>
			 * 
			 * <p> Vamos a definir dos alcances: </p>
			 * <ul>
			 * 	<li><b>admin-scope</p> tendra acceso a la administracion de usuarios</li>
			 * 	<li><b>user-scope</p> tendra acceso a los items</li>
			 * </ul>
			 * 
			 * TODO sacar a base de datos los clientes y usuarios.
			 * 
			 * @param auth
			 * @throws Exception
			 */
			public OAuth2Config() throws Exception {
				// Servicio que nos permite recuperarlas credenciales de los clientes autorizados
				// para acceder a nuestros recursos.
				ClientDetailsService csvc = new InMemoryClientDetailsServiceBuilder()
				        .withClient("blankClient_admin")
				        .authorizedGrantTypes("password")
				        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
				        .scopes("admin-scope", "user-scope")
				        .and()
				        .withClient("blankReader_user")
				        .authorizedGrantTypes("password")
				        .authorities("ROLE_CLIENT")
				        .scopes("user-scope")
				        .accessTokenValiditySeconds(3600)
				        .and().build();

				// Creamos una serie de usuarios con sus correspondientes roles
				// los roles tambien los creamos segun nuestras necesidades
				UserDetailsService svc = new InMemoryUserDetailsManager(Arrays.asList(
				        User.create("admin", "pass", "ADMIN", "USER"),
				        User.create("user0", "pass", "USER"), 
				        User.create("user1", "pass", "USER"),
				        User.create("user2", "pass", "USER"),
				        User.create("user3", "pass", "USER"),
				        User.create("user4", "pass", "USER"),
				        User.create("user5", "pass", "USER")));

				clientUserDetailService = new ClientAndUserDetailsService(csvc, svc);
			}

			/**
			 * Nos da acceso a los detalles de todos los clientes autorizados a acceder la aplicacion.
			 * @return ClientDetailsService
			 */
			@Bean
			public ClientDetailsService clientDetailsService() throws Exception {
				return clientUserDetailService;
			}

			/**
			 * Nos da acceso a los detalles de todos los usuarios de la aplicacion.
			 * @return UserDetailsService 
			 */
			@Bean
			public UserDetailsService userDetailsService() {
				return clientUserDetailService;
			}

			/**
			 * Indicamos al AuthorizationServerConfigurerAdapter que use AuhtenticationManager
			 * para procesar las peticiones de autenticacion.
			 */
			@Override
			public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
				endpoints.authenticationManager(authenticationManager);
			}

			/**
			 * Indicamos al AuthorizationServerConfigurerAdapter que use nuestro servidor
			 * de detales para autenticar a los clientes.
			 */
			@Override
			public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
				clients.withClientDetails(clientDetailsService());
			}

		}

	}

}
