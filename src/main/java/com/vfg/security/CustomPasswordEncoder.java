package com.vfg.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Realmente estamos usando el encoder mas estandar de Spring
 * pero si quisieramos cambiarlo solo tendriamos que tocar aqui.
 *
 * @author vifergo
 * @since v0.1
 */
@Component
public class CustomPasswordEncoder extends BCryptPasswordEncoder implements PasswordEncoder {

}
