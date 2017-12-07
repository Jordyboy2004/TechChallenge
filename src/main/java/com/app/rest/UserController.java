package com.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.err.UserFoundException;
import com.app.err.UserNotFoundException;
import com.app.jpa.UserRepository;
import com.app.model.User;

@RestController
@RequestMapping("/")
class UserController {

	private final UserRepository userRepository;

	@Autowired
	UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	User findUser(@PathVariable String userName) {
		this.validateUser(userName);
	
		return this.userRepository.findByUsername(userName);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	List<User> findUsers() {
	
		return this.userRepository.findAll();
	}

	@RequestMapping(value = "/{userName}", method = RequestMethod.PUT)
	ResponseEntity<Void> addUser(@PathVariable String userName, @RequestBody User input) {
		this.validateUserDoesNotExist(userName);
		
		User result = userRepository.save(new User(userName,input.getPassword(), input.getEmail()));;

		HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{userName}").buildAndExpand(result.getUsername()).toUri());
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.DELETE)
	ResponseEntity<Void> deleteUser(@PathVariable String userName) {
		this.validateUser(userName);
		
		@SuppressWarnings("unused")
		Long result = userRepository.deleteByUsername(userName);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED);

	}
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.POST)
	ResponseEntity<Void> updateUser(@PathVariable String userName, @RequestBody User input) {
		
		User user = this.userRepository.findByUsername(userName);
		
		if(input.getEmail() != null) {
			this.userRepository.updateEmail(input.getEmail());
		}
			
		if(input.getUsername() != null) {
			this.userRepository.updateUsername(input.getUsername(),user.getId());
		}

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.ACCEPTED);

	}

	private void validateUser(String userName) {
		if(this.userRepository.findByUsername(userName) == null)
		{
			throw new UserNotFoundException(userName);
		}
		
	}
	
	private void validateUserDoesNotExist(String userName) {
		if(!(this.userRepository.findByUsername(userName) == null))
		{
			throw new UserFoundException(userName);
		}
		
	}
}
