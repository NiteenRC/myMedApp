package com.fico.demo.controller;

import com.fico.demo.exception.CustomErrorType;
import com.fico.demo.model.User;
import com.fico.demo.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fico.demo.util.WebUrl.*;

@RestController
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepo userRepo;

	@RequestMapping(value = USER_FORGET_PASSWORD_EMAILID, method = RequestMethod.POST)
	public ResponseEntity<User> forgetPassword(@PathVariable String emailId) {
		User userResponse = userRepo.findByUserEmail(emailId);
		if (userResponse == null) {
			return new ResponseEntity(new CustomErrorType("There is no credential for this Email id!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@RequestMapping(value = USER_FORGET_PASSWORD, method = RequestMethod.POST)
	public ResponseEntity<User> passwordReset(@RequestBody User user) {
		User userResponse = userRepo.save(user);
		if (userResponse == null) {
			return new ResponseEntity(new CustomErrorType("There is no credential for this Email id!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@RequestMapping(value = USER, method = RequestMethod.POST)
	public ResponseEntity<User> registerNewUser(@RequestBody User user) {
		if (user.getUserName() == null) {
			return new ResponseEntity(new CustomErrorType("UserName is mandatory!!"), HttpStatus.NOT_FOUND);
		}

		User userResponse = userRepo.findByUserEmail(user.getUserEmail());
		if (userResponse != null) {
			return new ResponseEntity(new CustomErrorType("User email already exist!!"), HttpStatus.FOUND);
		}
		userResponse = userRepo.save(user);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@RequestMapping(value = USER_BY_EMAILID_PASSWORD, method = RequestMethod.POST)
	public ResponseEntity<User> checkUserCredentials(@RequestBody User user) {
		User userResponse = userRepo.findByUserEmailAndUserPassword(user.getUserEmail(), user.getUserPassword());
		if (userResponse == null) {
			return new ResponseEntity(new CustomErrorType("Please login with valid credentials!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
}
