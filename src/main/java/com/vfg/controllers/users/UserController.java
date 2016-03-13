package com.vfg.controllers.users;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

	@RequestMapping(value = "/{id}",  method = RequestMethod.GET)
	public Map<String, Object> userDetails(@PathVariable("id") String idUser) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", idUser);
		return model;
	}
	
	@RequestMapping(value = "/{id}",  method = RequestMethod.DELETE)
	public Map<String, Object> deleteUser(@PathVariable("id") String idUser) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", idUser);
		return model;
	}
}
