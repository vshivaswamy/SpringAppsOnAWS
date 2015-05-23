package userservice;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private static final String TEMPLATE = "Hello welcome to Dockerized UserService using Spring boot!";

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	public HttpEntity<UserResponse> start() throws Exception {
		UserResponse response = new UserResponse(TEMPLATE);
		response.add(linkTo(methodOn(UserController.class).getUsers())
				.withSelfRel());
		return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, headers = { "Accept=application/json,application/xml" }, produces = {
			"application/json", "application/xml" })
	public UserResponse getUsers() throws Exception {
		UserResponse response = new UserResponse();
		List<User> users = userRepository.getUsers();
		response.setUsers(users);
		return response;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, headers = { "Accept=application/json,application/xml" }, produces = {
			"application/json", "application/xml" })
	public User getUserById(@PathVariable("id") String id) throws Exception {
		User user = userRepository.getUser(Integer.parseInt(id));

		return user;
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST, headers = { "Accept=application/json,application/xml" }, produces = {
			"application/json", "application/xml" })
	public User addUser(@RequestBody User user) throws Exception {
		if (user == null)
			return null;
		userRepository.addUser(user);
		return user;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST, headers = { "Accept=application/json,application/xml" }, produces = {
			"application/json", "application/xml" })
	public User updateUser(@RequestBody User user, @PathVariable String id)
			throws Exception {
		if (user == null && id == null)
			return null;
		user.setId(Integer.parseInt(id));
		userRepository.updateUser(user);
		return userRepository.getUser(Integer.parseInt(id));
	}
}
