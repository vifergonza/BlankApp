package com.vfg.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/about")
@RestController
public class AboutController {

	@Value("${blank.about.version}")
	private String version;

	@Value("${blank.about.author}")
	private String author;

	@Value("${blank.about.mail}")
	private String mail;
	
	@Value("${blank.about.twitter}")
	private String twitter;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> about(Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("author", author);
		model.put("mail", mail);
		model.put("twitter", twitter);
		model.put("version", version);
		return model;
	}
	
}
