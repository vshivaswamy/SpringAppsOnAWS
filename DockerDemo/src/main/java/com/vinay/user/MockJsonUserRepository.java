package com.vinay.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author Vinays
 * 
 */
@Repository
public class MockJsonUserRepository {

	@Cacheable(value = { "usersCache" })
	public UserResponse getUsers() throws Exception {
		UserResponse userResponse = loadUsers();
		return userResponse;
	}

	private UserResponse loadUsers() throws Exception {
		Resource  resource = new ClassPathResource(MockUserConstants.USERS_JSON_FILE);
		System.out.println("file path -----"+resource.getURI());
		return (UserResponse) new com.fasterxml.jackson.databind.ObjectMapper()
				.readValue(resource.getInputStream(),
						UserResponse.class);
	}
}
