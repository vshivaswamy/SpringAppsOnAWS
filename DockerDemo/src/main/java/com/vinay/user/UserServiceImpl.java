package com.vinay.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Service;

/**
 * @author Vinays
 * 
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MockJsonUserRepository mockJsonUserRepository;

	private Cache usersCache = null;

	@SuppressWarnings("unchecked")
	@Override
	public UserResponse getUsers() throws Exception {
		ConcurrentMap<String, User> map = (ConcurrentMap<String, User>) usersCache
				.getNativeCache();
		UserResponse userResponse = new UserResponse();
		List<User> userList = new ArrayList<User>(map.values());
		userResponse.setUsers(userList);
		return userResponse;
	}

	@Override
	public User getUserById(String id) {
		return usersCache.get(id, User.class);
	}

	@Override
	public void addUser(User user) {
		usersCache.put(user.getId(), user);
	}

	@Override
	public void updateUser(final String id, User user) {
		usersCache.evict(id);
		usersCache.put(id, user);
	}

	private Cache loadUsersCache() throws Exception {
		if (usersCache != null)
			return usersCache;
		usersCache = new ConcurrentMapCache("usersCache");
		UserResponse userResponse = mockJsonUserRepository.getUsers();
		if (userResponse != null && userResponse.getUsers() != null) {
			for (User user : userResponse.getUsers())
				usersCache.putIfAbsent(user.getId(), user);
		}
		return usersCache;
	}

	@PostConstruct
	public void init() throws Exception {
		loadUsersCache();
	}
}
