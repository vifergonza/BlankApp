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
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import com.vfg.security.CustomPasswordEncoder;
import com.vfg.security.OAuth2ClientConstants;
import com.vfg.services.ClientOAuth2DetailsServiceI;
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
         *
         * Realizamos una configuración bastante generica de la seguridad de cada recurso.
         * El filtrado mas fino se realizará en los propios servicios.
         */
        @Override
        public final void configure(final HttpSecurity http) throws Exception {
            // CSRF es un token de proteccion para accesos web.
            // Habra que activarlo si queremos usar terceros como autenticadores
            // (facebook, twitter, google)
            http.csrf().disable();

            // Permitimos acceso anonimo a la solicitud de tokens.
            http.authorizeRequests().antMatchers("/oauth/token").anonymous();

            // Exigimos que cualquier peticion get tenga el scope de cliente normal.
            http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**")
                    .access("#oauth2.hasScope('" + OAuth2ClientConstants.Scopes.USER_SCOPE.getScope() + "')");

            // El resto de peticiones necesitaran el scope administrador.
            http.authorizeRequests()
                .antMatchers("/**")
                    .access("#oauth2.hasScope('" + OAuth2ClientConstants.Scopes.ADMIN_SCOPE.getScope() + "')");
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
             * Servicio de usuarios propio de la aplicacion.
             */
            @Autowired
            private UserServiceI customUserService;

            /**
             * Este bean es cargado por spring security para acceder al servicio de usuarios.
             * Al sobreescribirlo le indicamos que use el que definimos nosotros y no el servicio
             * por defecto.
             *
             * @return UserDetailsService Nos da acceso a los detalles de todos los usuarios de la aplicacion.
             */
            @Bean
            public UserDetailsService userDetailsService() {
                return customUserService;
            }

            /**
             * Servidor de detalles de los clientes que pueden acceder a nuestro
             * servidor protegido por OAuth2.
             */
            @Autowired
            private ClientOAuth2DetailsServiceI customClientDetailsService;

            /**
             * Este bean es cargado por spring security para acceder al servicio de clientes.
             * Al sobreescribirlo le indicamos que use el que definimos nosotros y no el servicio
             * por defecto.
             *
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
             * servidor de detalles para autenticar a los clientes.
             */
            @Override
            public final void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
                clients.withClientDetails(clientDetailsService());
            }
        }
    }
}
