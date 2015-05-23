package userservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserServiceApplication.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0", "server.contextPath=/userservice" })
@ActiveProfiles("local")
@Transactional
public class UserRepositoryIT {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void getUsers() throws Exception{
		List<User> users = userRepository.getUsers();
		assertNotNull(users);
	}
	
	@Test
	public void getUser() throws Exception {
		User user = userRepository.getUser(1);
		assertNotNull(user);
		assertEquals(user.getFirstName(), "Vinay");
	}
	
	@Test
	public void addUser() throws Exception {
		User user = new User("John", "Doe", "john.doe@email.com","373-333-1918");
		userRepository.addUser(user);
		User newUser = userRepository.getUser(user.getId());
		assertEquals(user.getEmail(), newUser.getEmail());
	}
	
	@Test
	public void updateUser() throws Exception {
		User user = new User(1,"Vinay", "Shivaswamy", "vinay@email.com","123-333-1918");
		user = userRepository.updateUser(user);
		User newUser = userRepository.getUser(user.getId());
		assertEquals(user.getEmail(), newUser.getEmail());
	}

}
