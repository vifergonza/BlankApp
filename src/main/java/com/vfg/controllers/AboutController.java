package com.vfg.controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/about")
@RestController
public class AboutController {
	
	private static Logger log = Logger.getLogger(AboutController.class);

	@Value("${blank.about.version}")
	private String version;

	@Value("${blank.about.author}")
	private String author;

	@Value("${blank.about.mail}")
	private String mail;
	
	@Value("${blank.about.twitter}")
	private String twitter;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> about() {
		log.debug("/about GET");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("author", author);
		model.put("mail", mail);
		model.put("twitter", twitter);
		model.put("version", version);
		return model;
	}
	
}
