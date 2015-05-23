package com.vinay.user;

/**
 * @author Vinays
 * 
 */
public interface UserService {

    public UserResponse getUsers() throws Exception;

    public User getUserById(String id);

    public void addUser(User user);

    public void updateUser(final String id, User user);
}
