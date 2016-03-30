# Servicio RESTful protegido por OAuth2

* Spring Boot 1.3.3.RELEASE
* Spting Security
* Spring Security OAuth2 2.0.9.RELEASE
* JPA
* H2database (base de datos en memoria)
* Log4j
* Junit

Este es el proyecto básico a partir del cual construir servicios REST protegido con el protocolo **OAuth2**. Este proyecto se centra en el lado del servidor y no incluye la implementación de ningún cliente. Creo que es la mejor forma de comprender el concepto de servicio REST puro, de esta forma evitamos que nuestro subconsciente nos manipule y hagamos concesiones pensando en las arquitecturas que se conectarán a él.

Este proyecto es al tiempo servidor de recursos y servidor de autorizaciones. La configuracion de ambos aspectos se encuentra en el fichero **BlankAppConfig**.

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

-----------------------------------------------------

## Formatos admitidos en las peticiones
Ambos métodos aceptan el envío de datos en varios formatos seleccionable mediante *Headers*:

* **Content-type: application/x-www-form-urlencoded** si enviamos los datos dentro del *body* de la petición http. El formato será *param=valor1&param2=valor2*.
* **Content-type: application/json** si enviamos los datos dentro del *body* de la petición http en formato *json*. Por ejemplo *{"param":"valor1", "param2":"valor2"}*.
* **Content-type: application/xml** si enviamos los datos dentro del *body* de la petición http en formato *xml*. Por ejemplo *<nodo><param>valor1</param><param2>valor2</param2></nodo>*. Para que esto funcione correctamente, las clases recibidas por el servicio deben estar debidamente anotadas. Ver *RequestDto.java*.

## Formatos admitidos en las respuestas
Podemos elegir el formato en el cual el servidor nos retorna los datos, también mediante *Headers*:

* **Accept: application/json** para que el servidor nos retorne la información en *json*.
* **Accept: application/xml** para que el servidor nos retorne la información en *xml*. Para que esto funcione correctamente, las clases retornadas por el servicio deben estar debidamente anotadas. Ver *ResponseDto.java*.

## Seleccion de idioma
Tambien podemos establecer el idioma en el que queremos que el servidor nos responda mediante la cabecera *Accept-Language: en*

###### Otros datos
En el fichero *pruebasRest.sh* hay una bateria de pruebas *curl* con las cabeceras comentadas anteriormente.