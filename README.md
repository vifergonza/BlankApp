# Servicio RESTful protegido por OAuth2

* Spring Boot 1.3.3.RELEASE
* Spting Security
* Spring Security OAuth2 2.0.9.RELEASE
* JPA
* H2database (base de datos en memoria)
* Log4j
* Junit

Este es un proyecto básico a partir del cual construir servicios REST protegido con el protocolo **OAuth2**. El proyecto se centra en el lado del servidor y no incluye la implementación de ningún cliente. Creo que es la mejor forma de comprender el concepto de servicio REST puro, de esta forma evitamos que nuestro subconsciente nos manipule y hagamos concesiones pensando en las arquitecturas que se conectarán a él.

La aplicación es al tiempo servidor de recursos y servidor de autorizaciones. La configuracion de ambos aspectos se encuentra en el fichero **BlankAppConfig**.

## Servidor de autenticaciones

La configuración del servidor de autenticaciones se realiza en la clase protegida **OAuth2Config** la cual se encuentra dentro de la clase general de configuración _BlankAppConfig_. Para ello la clase ha de estar anotada con _@EnableAuthorizationServer_ y extender de _AuthorizationServerConfigurerAdapter_.
Realmente lo único que realiza esta clase es indicar al servidor de auntenticaciones proporcionado por Spring, quienes son el _AuthenticationManager_ (clase encargada de realizar la autenticación de los usuarios), el _ClientDetailsService_ (clase que permite el acceso a los datos de los clientes registrados en la aplicación), y el _UserDetailService_ (clase que permite el acceso a los datos de los usuarios registrados en la aplicación).

## Servidor de recursos

La clase **ResourceServer** (también dentro de _BlankAppConfig_) se encargar de la configuración del servidor de recursos. Para ello la clase ha de estar anotada con _@EnableResourceServer_ y extender de _ResourceServerConfigurerAdapter_. Esto nos permite sobreescribir el metodo:

	@Override
    public final void configure(final HttpSecurity http) throws Exception

Aqui podremos especificar, entre otras cosas, las credenciales necesarias para acceder a cada recurso.
Inicialmente partimos con tres:

+	La url _/oauth/token_ estará disponible para peticiones anonimas ya que es la usada para solicitar tokens de acceso.
+	Las peticiones _GET_ estarán autorizadas para clientes con el scope _USER_SCOPE_.
+	El resto de peticiones (_POST_, _PUT_, _DELETE_) estarán autorizadas para clientes con el scope _ADMIN_SCOPE_.

Con estas dos simples restricciones bloqueamos el acceso a nuestros recursos a cualquier aplicación que no haya sido registrada previamente como cliente autorizado.

La seguridad mas "fina", la especifica de cada funcionalidad segun roles y contenido solicitado se hará en cada servicio mediante anotaciones _@PreAuthorize_ y _@PostAuthorize_

## Definicion de clientes

La configuración de los clientes se realiza en el servicio _ClientOAuth2DetailsServiceImpl_.
A la hora de definir un cliente, a parte del identificador y la contraseña (_secret_) es muy importante definir los **GRANT_TYPES** (los modos en los que el cliente interactuará con el servidor OAuth2) y los **SCOPES** (o ambitos de acceso). Los _GRANT_TYPE_ son cuatro y estan definidos por el estándar de OAuth2. Sin embargo los _SCOPES_ (al igual que los roles de usuario) son definidos por nosotros en función de los requerimientos de nuestro proyecto.

Inicialmente creamos dos ámbitos uno de lectura y otro de control total. Esto nos permite crear clientes que sólo puedan leer nuestros recursos y clientes que puedan leer y escribir en nuestro servidor. También sería posible crear ambitos que permitiesen sólo permitiesen acceder a determinadas URLs de la aplicación y multitud de combinaciones más.

Por último comentar que, aunque veamos que los clientes estan _hardcodeados_, podrián estar perfectamente en la base de datos (al igual que los usuarios) y permitir la creación de nuevos clientes a través de la propia aplicación.