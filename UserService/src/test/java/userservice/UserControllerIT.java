package userservice;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserServiceApplication.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0", "server.contextPath=/userservice" })
@PropertySource("classpath:application.properties")
@ActiveProfiles("local")
public class UserControllerIT {

	 @Value("${local.server.port}")
	    private int port;

	    @Value("${server.contextPath}")
	    private String context;

	    private URL base;
	    private RestTemplate template;
	    
	    @Before
	    public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + context);
		template = new TestRestTemplate();
	    }

	    @Test
	    public void getUsers() throws Exception {
	    	ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<List<User>>() { };
		ResponseEntity<List<User>> response = template.exchange(
			base.toString() + "/1.0/users",
			HttpMethod.GET, null, responseType);
		List<User> users = response.getBody();
		assertThat(users.get(0).getFirstName(), equalTo("Vinay"));
	    }

}
