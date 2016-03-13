package com.vfg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.vfg.configurations.Oauth2Config;

@EnableAutoConfiguration
@EnableWebMvc
@Configuration
@Import(Oauth2Config.class)
@ComponentScan
public class BlankApplication extends RepositoryRestMvcConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(BlankApplication.class, args);
	}
}
