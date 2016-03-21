package com.vfg.configurations;

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

import com.vfg.security.CustomPasswordEncoder;
import com.vfg.services.UserServiceI;

/**
 * Clase principal de configuracion de la aplicacion.
 * @author vifergo
 * @since v0.1
 */
@Configuration
public class BlankAppConfig {

    /**
     * Modificador para el orden de los filtros.
     */
    protected static final int ORDER_100 = 100;

    /**
     * <p>Configuramos Spring Security para que use el nuestro UserService.</p>
     *
     * @author vifergo
     * @since v0.1
     */
    @Configuration
    @EnableWebSecurity
    protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        /**
         * Servicio de acceso a usuarios.
         */
        @Autowired
        private UserServiceI customUserService;

        /**
         * Componente de encriptacion para las contraseñas.
         */
        @Autowired
        private CustomPasswordEncoder customPasswordEncoder;

        /**
         * {@inheritDoc}
         */
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected final void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserService).passwordEncoder(customPasswordEncoder);
        }

    }

    /**
     * <p>Configuramos la aplicacion como servidor de recursos.</p>
     *
     * <p>Desde aqui configuraremos, entre otras cosas, los permisos de acceso a
     * los recursos en funcion de las rutas. Dado que tratamos de construir un
     * servicio REST deberemos prestar atencion especial a los permisos que
     * damos a metodos como POST, PUT y DELETE.</p>
     *
     * @author vifergo
     * @since v0.1
     *
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServer extends ResourceServerConfigurerAdapter {

        /**
         * {@inheritDoc}
         */
        @Override
        public final void configure(final HttpSecurity http) throws Exception {
            // CSRF es un token de proteccion para accesos web.
            // Habra que activarlo si queremos usar terceros como autenticadores
            // (facebook, twitter, google)
            http.csrf().disable();

            // Permitimos acceso anonimo a la solicitud de tokens
            http.authorizeRequests().antMatchers("/oauth/token").anonymous();

            // Permitimos acceso libre al recurso "about" y todos sus
            // descendientes
            http.authorizeRequests().antMatchers("/about/**").permitAll();

            // autorizamos todos los get de momento
            http.authorizeRequests().antMatchers(HttpMethod.GET, "/**").anonymous();

            // Exigimos que cualquier peticion get tenga el scope 'read'
            // http.authorizeRequests().antMatchers(HttpMethod.GET,
            // "/**").access("#oauth2.hasScope('read')");

            // Exigimos que cualquier peticion que no encaje con los parametros
            // anteriores
            // tenga el scope write
            // http.authorizeRequests().antMatchers("/**").access("#oauth2.hasScope('write')");
        }

        /**
         * <p>Configuramos la aplicacion con servidor de autenticacion Oauth2.</p>
         *
         * @author vifergo
         * @since v0.1
         */
        @Configuration
        @EnableAuthorizationServer
        @Order(Ordered.LOWEST_PRECEDENCE - BlankAppConfig.ORDER_100)
        protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

            /**
             * Proveedor de autenticacion.
             */
            @Autowired
            @Qualifier("authenticationManagerBean")
            private AuthenticationManager authenticationManager;

            /**
             * Servicio de usuarios.
             */
            @Autowired
            private UserServiceI customUserService;

            /**
             * Servidor de detalles de los clientes que pueden acceder a nuestro
             * servidor rest.
             */
            private ClientDetailsService customClientDetailsService;

            /**
             * <p>Incializamos OAuth2.</p>
             * <p>Como punto de partida harcodeamos los clientes,
             * pero esto no deberia hacerse asi jamas.<p>
             *
             * <p>Vamos a definir dos alcances:</p>
             * <ul>
             * <li><b>admin-scope</b> tendra acceso a la administracion de usuarios.</li>
             * <li><b>user-scope</b> tendra acceso a los items.</li>
             * </ul>
             *
             * @throws Exception en caso de errores en la creacion del client service.
             */
            public OAuth2Config() throws Exception {
                // Servicio que nos permite recuperarlas credenciales de los
                // clientes autorizados
                // para acceder a nuestros recursos.
                customClientDetailsService = new InMemoryClientDetailsServiceBuilder().withClient("blankClient_admin")
                        .authorizedGrantTypes("password").authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                        .scopes("admin-scope", "user-scope").and().withClient("blankReader_user")
                        .authorizedGrantTypes("password").authorities("ROLE_CLIENT").scopes("user-scope")
                        .accessTokenValiditySeconds(3600).and().build();
            }

            /**
             * @return ClientDetailsService Nos da acceso a los detalles de todos los
             *                              clientes autorizados a acceder la aplicacion.
             *
             * @throws Exception excepcion en la carga del servicio de clientes.
             */
            @Bean
            public ClientDetailsService clientDetailsService() throws Exception {
                return customClientDetailsService;
            }

            /**
             * @return UserDetailsService Nos da acceso a los detalles de todos los usuarios de la aplicacion.
             */
            @Bean
            public UserDetailsService userDetailsService() {
                return customUserService;
            }

            /**
             * Indicamos al AuthorizationServerConfigurerAdapter que use
             * AuhtenticationManager para procesar las peticiones de
             * autenticacion.
             *
             * {@inheritDoc}
             */
            @Override
            public final void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
                endpoints.authenticationManager(authenticationManager);
            }

            /**
             * {@docRoot}
             *
             * Indicamos al AuthorizationServerConfigurerAdapter que use nuestro
             * servidor de detales para autenticar a los clientes.
             */
            @Override
            public final void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
                clients.withClientDetails(clientDetailsService());
            }
        }
    }
}
