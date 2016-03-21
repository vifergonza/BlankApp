package com.vfg.security;

/**
 * Constantes para la configuracion de los clientes de OAuth2.
 *
 * @author vifergo
 * @since v0.1
 */
public final class OAuth2ClientConstants {

    /**
     * Al tratarse de una clase de servicios públicos, creamos un constructor privado para
     * ocultar el constructor publico por defecto.
     */
    private OAuth2ClientConstants() {
    }

    /**
     * Validez del token de acceso.
     */
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS =  3600;

    /**
     * Ambitos definidos para los clientes OAuth2.
     *
     * @author vifergo
     * @since v0.1
     */
    public enum Scopes {

        /**
         * Ambito total.
         */
        ADMIN_SCOPE("admin-scope"),

        /**
         * Ambito restringido.
         */
        USER_SCOPE("user-scope");

        /**
         * Valor del ambito.
         */
        private final String scope;

        /**
         * Constructor.
         * @param value valor.
         */
        Scopes(final String value) {
            this.scope = value;
        }

        /**
         * @return el valor del ambito
         */
        public String getScope() {
            return this.scope;
        }
    }

    /**
     * Autorities definidas para OAuth2.
     *
     * @author vifergo
     * @since v0.1
     */
    public enum Authority {

        /**
         * Role Client.
         */
        ROLE_CLIENT("ROLE_CLIENT"),

        /**
         * Role trusted client.
         */
        ROLE_TRUSTED_CLIENT("ROLE_TRUSTED_CLIENT");

        /**
         * Nombre del authority.
         */
        private final String authority;

        /**
         * Constructor.
         * @param value authority
         */
        Authority(final String value) {
            this.authority = value;
        }

        /**
         * @return el valor del authority.
         */
        public String getAuthority() {
            return this.authority;
        }
    }

    /**
     * Grant types definidos para OAuth2.
     * @author vifergo
     * @since v0.1
     */
    public enum AuthorizedGrantTypes {

        /**
         * Password.
         */
        PASSWORD("password"),

        /**
         * Implicito.
         */
        IMPLICIT("implicit"),

        /**
         * Credenciales del cliente.
         */
        CLIENT_CREDENTIALS("client_credentials"),

        /**
         * Token de refresco.
         */
        REFRESH_TOKEN("refresh_token"),

        /**
         * Codigo de autorizacion.
         */
        AUTHORIZATION_CODE("authorization_code");

        /**
         * Nombre del tipo.
         */
        private final String type;

        /**
         * Constructor.
         * @param value nombre del tipo.
         */
        AuthorizedGrantTypes(final String value) {
            this.type = value;
        }

        /**
         * @return nombre del tipo.
         */
        public String getType() {
            return this.type;
        }
    }

}
