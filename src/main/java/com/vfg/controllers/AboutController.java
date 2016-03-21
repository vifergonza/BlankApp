package com.vfg.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contiene peticiones relativas a metadatos de la aplicacion.
 *
 * @author vifergo
 * @since v0.1
 */
@RequestMapping("/about")
@RestController
public class AboutController {

    /**
     * Logger.
     */
    private static Logger log = Logger.getLogger(AboutController.class);

    /**
     * Version de la aplicacion.
     */
    @Value("${blank.about.version}")
    private String version;

    /**
     * Autor de la aplicacion.
     */
    @Value("${blank.about.author}")
    private String author;

    /**
     * Mail de contacto.
     */
    @Value("${blank.about.mail}")
    private String mail;

    /**
     * Twitter de contacto.
     */
    @Value("${blank.about.twitter}")
    private String twitter;

    /**
     * @return Informacion relativa al autor y la version de la aplicacion.
     */
    @RequestMapping(method = RequestMethod.GET)
    public final Map<String, Object> about() {
        log.debug("/about GET");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("author", author);
        model.put("mail", mail);
        model.put("twitter", twitter);
        model.put("version", version);
        return model;
    }

}
