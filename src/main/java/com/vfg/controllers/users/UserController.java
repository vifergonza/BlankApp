package com.vfg.controllers.users;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vfg.repository.User;
import com.vfg.services.UserService;

@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Collection<User> userList() {
		return userService.findUsers();
	}
	
	@RequestMapping(path = "/{id}",  method = RequestMethod.GET)
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
