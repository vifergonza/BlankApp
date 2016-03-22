package com.vfg.oauth2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import com.vfg.BlankApplication;

/**
 * Pruebas sobre los clientes de tipo password.
 *
 * @author vifergo
 * @since v0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BlankApplication.class)
@WebAppConfiguration
public class PasswordGrantTypeClientTest {

    /**
     * Url para la obtencion de tokens de acceso.
     */
    private static final String URL_TOKEN = "/oauth/token";

    /**
     * Contexto de la aplicacion.
     */
    @Autowired
    private WebApplicationContext context;

    /**
     * Filtro de spring security.
     */
    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    /**
     * Emulador de mvc.
     */
    private MockMvc mvc;

    /**
     * Inicializamos el emulador de MVC.
     */
    @Before
    public final void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
    }
//
//    @Test
//    public void shouldHavePermission() throws Exception {
//        mvc.perform(
//                get("/api/resource").header("Authorization", "Bearer " + getAccessToken("user", "123")).accept(
//                        MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//    }

    /**
     * Tratamos de recuperar los tokens de acceso con varios usuarios.
     */
    @Test
    public final void testAccessToken() {
        final String client = "blankClient_admin";
        String secret = "admin_blankClient";

        try {
            MockHttpServletResponse response = getAccessToken(client, secret, "vfg", "vfg");
            Assert.assertEquals(response.getStatus(), MockHttpServletResponse.SC_OK);
            String accessToken = new ObjectMapper()
                .readValue(response.getContentAsByteArray(), OAuthToken.class).getAccessToken();
            Assert.assertNotNull(accessToken);

            response = getAccessToken(client, secret, "user", "user");
            Assert.assertEquals(response.getStatus(), MockHttpServletResponse.SC_BAD_REQUEST);

            response = getAccessToken(client, secret, "editor", "editor");
            Assert.assertEquals(response.getStatus(), MockHttpServletResponse.SC_BAD_REQUEST);

            response = getAccessToken(client, secret, "user2", "user2");
            Assert.assertEquals(response.getStatus(), MockHttpServletResponse.SC_BAD_REQUEST);

            response = getAccessToken(client, secret, "user3", "user3");
            Assert.assertEquals(response.getStatus(), MockHttpServletResponse.SC_OK);
            accessToken = new ObjectMapper()
            .readValue(response.getContentAsByteArray(), OAuthToken.class).getAccessToken();

        Assert.assertNotNull(accessToken);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Realiza la petición del token de acceso.
     *
     * @param client identificador del cliente
     * @param secret password del cliente
     * @param username nombre de usuario
     * @param password contraseña del usuario
     * @return MockHttpServletResponse respuesta del servidor
     * @throws Exception si se produce algun error
     */
    private MockHttpServletResponse getAccessToken(final String client, final String secret,
                                        final String username, final String password) throws Exception {
        MockHttpServletResponse response = mvc
                .perform(
                        post(URL_TOKEN)
                                .header("Authorization", "Basic " + getBasicEncode(client, secret))
                                .param("username", username).param("password", password)
                                .param("grant_type", "password")).andReturn().getResponse();
        return response;
    }

    /**
     * Retorna el cliente y su pasword codificados.
     *
     * @param client identificador del cliente
     * @param secret password del cliente
     * @return identificador:password codificados en base64.
     */
    private String getBasicEncode(final String client, final String secret) {
        return new String(Base64Utils.encode((client + ":" + secret).getBytes()));
    }

    /**
     * Clase para mapear a string la respuesta json del servidor.
     *
     * @author vifergo
     * @since v0.1
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OAuthToken {

        /**
         * Token de acceso.
         */
        @JsonProperty("access_token")
        private String accessToken;

        /**
         * @return token de acceso
         */
        public final String getAccessToken() {
            return accessToken;
        }

        /**
         * @param token de acceso
         */
        public final void setAccessToken(final String token) {
            this.accessToken = token;
        }
    }
}
