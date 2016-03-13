package com.vfg.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/about")
@RestController
public class AboutController {

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> about(Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("author", "Victor Fernandez");
		if (null!=principal) {
			model.put("mail", "vifergonza@gmail.com");
		}
		return model;
	}
	
	@RequestMapping(value = "/authordetails", method = RequestMethod.GET)
	public Map<String, Object> privateAbout(Principal principal) {
		Map<String, Object> model = about(principal);
		model.put("telefono", "555-666-666");
		return model;
	}
	
	@RequestMapping(value = "/version",method = RequestMethod.GET)
	public Map<String, Object> version() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("version", "0.0.0");
		return model;
	}
}
