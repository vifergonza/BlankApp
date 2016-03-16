package com.vfg;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.vfg.configurations.Oauth2Config;
import com.vfg.repository.UserRepository;
import com.vfg.services.UserService;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableWebMvc
@Configuration
@Import(Oauth2Config.class)
@ComponentScan
public class BlankApplication extends RepositoryRestMvcConfiguration implements CommandLineRunner {
	
	private static Logger log = Logger.getLogger(BlankApplication.class);
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(BlankApplication.class, args);
	}

	/**
	 * Este metodo se ejecuta al terminar la carga de la aplicacion.
	 * Nos permite hacer inicializaciones y comprobaciones.
	 */
	@Override
    public void run(String... arg0) throws Exception {
		log.info("Iniciando carga de roles y usuarios");
	    userService.initDatabase();
		log.info("Finalizando carga de roles y usuarios");
    }
	
}
