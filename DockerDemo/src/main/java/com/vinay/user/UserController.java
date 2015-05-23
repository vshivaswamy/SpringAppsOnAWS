package com.vinay.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vinays
 * 
 */
@RestController
public class UserController {

	private static final String TEMPLATE = "Hello welcome to Dockerized UserService using Spring boot!";

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	public HttpEntity<UserResponse> start() throws Exception {
		UserResponse response = new UserResponse(TEMPLATE);
		response.add(linkTo(methodOn(UserController.class).getUsers())
				.withSelfRel());
		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, headers = { "Accept=application/json,application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseBody
	public UserResponse getUsers() throws Exception {
		UserResponse users = userService.getUsers();

		return users;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = { "Accept=application/json,application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseBody
	public User getUserById(@PathVariable("id") String id,
			HttpServletRequest request) throws Exception {
		User user = userService.getUserById(id);

		return user;
	}

	@RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json,application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseBody
	public User addUser(@RequestBody User user, HttpServletRequest request)
			throws Exception {
		if (user == null)
			return null;
		userService.addUser(user);
		return userService.getUserById(user.getId());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, headers = { "Accept=application/json,application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseBody
	public User updateUser(@RequestBody User user, @PathVariable String id,
			HttpServletRequest request) throws Exception {
		if (user == null)
			return null;
		userService.updateUser(id, user);
		return userService.getUserById(user.getId());
	}
}
