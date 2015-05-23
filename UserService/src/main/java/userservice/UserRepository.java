package userservice;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<User> getUsers() {
		String SELECT_USERS = "SELECT * FROM user";
		List<User> users = namedParameterJdbcTemplate.query(SELECT_USERS,
				new UserRowMapper());
		LOGGER.info("Fetched users:" + users.size());
		return users;
	}
	
	public User getUser(int id) {
		String SELECT_USER = "SELECT * FROM user where id = :id";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();    
		paramMap.addValue("id", id);    
		User user = (User)namedParameterJdbcTemplate.queryForObject(SELECT_USER,paramMap,
				new UserRowMapper());
		LOGGER.info("Fetched users:" + user.getId());
		return user;
	}

	public User addUser(User user) {
		String INSERT_USER = "INSERT INTO user(FIRSTNAME,LASTNAME,EMAIL,PHONE) VALUES (:firstName,:lastName,:email,:phone)";
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();    
		paramMap.addValue("firstName", user.getFirstName());   
		paramMap.addValue("lastName", user.getLastName());   
		paramMap.addValue("email", user.getEmail());   
		paramMap.addValue("phone", user.getPhone());   
	    namedParameterJdbcTemplate.update(INSERT_USER, paramMap, generatedKeyHolder);  
	    user.setId(generatedKeyHolder.getKey().intValue());
		return user;
	}
	
	public User updateUser(User user) {
		String UPDATE_USER = "UPDATE user SET FIRSTNAME = :firstName, LASTNAME = :lastName, EMAIL = :email, PHONE = :phone WHERE ID = :id";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();    
		paramMap.addValue("id", user.getId());     
		paramMap.addValue("firstName", user.getFirstName());   
		paramMap.addValue("lastName", user.getLastName());   
		paramMap.addValue("email", user.getEmail());   
		paramMap.addValue("phone", user.getPhone());   
	    namedParameterJdbcTemplate.update(UPDATE_USER, paramMap);  
		return user;
	}
}
