package com.vfg.services.impl;

import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.vfg.security.OAuth2ClientConstants;
import com.vfg.services.ClientOAuth2DetailsServiceI;

/**
 * Servicio de detalles de clientes de OAtuh2 aceptados por la aplicacion.
 * @author vifergo
 * @since v0.1
 */
@Service
public class ClientOAuth2DetailsServiceImpl implements ClientOAuth2DetailsServiceI {

    /**
     * Servicio interno.
     */
    private ClientDetailsService internalClientDetailsService;

    /**
     * Constructor que inicializa el servidor de clientes OAuth2.
     * @throws Exception en la creacion de los clientes.
     */
    public ClientOAuth2DetailsServiceImpl() throws Exception {
        internalClientDetailsService = new InMemoryClientDetailsServiceBuilder()
            // Creamos un cliente administrador. El grant type password nos obliga a usarlo
            // siempre con un usuario y contraseña.
            .withClient("blankClient_admin")
                .secret("admin_blankClient")
                .authorizedGrantTypes(OAuth2ClientConstants.AuthorizedGrantTypes.PASSWORD.getType())
                .authorities(OAuth2ClientConstants.Authority.ROLE_CLIENT.getAuthority(),
                        OAuth2ClientConstants.Authority.ROLE_TRUSTED_CLIENT.getAuthority())
                .scopes(OAuth2ClientConstants.Scopes.ADMIN_SCOPE.getScope(),
                        OAuth2ClientConstants.Scopes.USER_SCOPE.getScope())
            .and()
            // Creamos un cliente solo lectura. Añadimos el grant type client_credentials para
            // permitir el acceso de ese cliente sin usuario.
            .withClient("blankReader_user")
                .secret("user_blankReader")
                .authorizedGrantTypes(OAuth2ClientConstants.AuthorizedGrantTypes.PASSWORD.getType(),
                        OAuth2ClientConstants.AuthorizedGrantTypes.CLIENT_CREDENTIALS.getType())
                .authorities(OAuth2ClientConstants.Authority.ROLE_CLIENT.getAuthority())
                .scopes(OAuth2ClientConstants.Scopes.USER_SCOPE.getScope())
                .accessTokenValiditySeconds(OAuth2ClientConstants.ACCESS_TOKEN_VALIDITY_SECONDS)
            .and()
            .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ClientDetails loadClientByClientId(final String client) throws ClientRegistrationException {
        return internalClientDetailsService.loadClientByClientId(client);
    }

}
