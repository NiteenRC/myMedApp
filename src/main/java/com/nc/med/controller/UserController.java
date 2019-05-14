package com.nc.med.controller;

import static com.nc.med.util.WebUrl.USER;
import static com.nc.med.util.WebUrl.USER_BY_EMAILID_PASSWORD;
import static com.nc.med.util.WebUrl.USER_FORGET_PASSWORD;
import static com.nc.med.util.WebUrl.USER_FORGET_PASSWORD_EMAILID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.exception.CustomErrorType;
import com.nc.med.model.User;
import com.nc.med.repo.UserRepo;

@RestController
public class UserController {
	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepo userRepo;

	@RequestMapping(value = USER_FORGET_PASSWORD_EMAILID, method = RequestMethod.POST)
	public ResponseEntity<?> forgetPassword(@PathVariable String emailId) {
		User userResponse = userRepo.findByUserEmail(emailId);
		if (userResponse == null) {
			return new ResponseEntity<>(new CustomErrorType("There is no credential for this Email id!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@RequestMapping(value = USER_FORGET_PASSWORD, method = RequestMethod.POST)
	public ResponseEntity<?> passwordReset(@RequestBody User user) {
		User userResponse = userRepo.save(user);
		if (userResponse == null) {
			return new ResponseEntity<>(new CustomErrorType("There is no credential for this Email id!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@RequestMapping(value = USER, method = RequestMethod.POST)
	public ResponseEntity<?> registerNewUser(@RequestBody User user) {
		if (user.getUserName() == null) {
			LOGGER.info("UserName is mandatory!!");
			return new ResponseEntity<>(new CustomErrorType("UserName is mandatory!!"), HttpStatus.NOT_FOUND);
		}

		User userResponse = userRepo.findByUserEmail(user.getUserEmail());
		if (userResponse != null) {
			return new ResponseEntity<>(new CustomErrorType("User email already exist!!"), HttpStatus.FOUND);
		}
		userResponse = userRepo.save(user);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@RequestMapping(value = USER_BY_EMAILID_PASSWORD, method = RequestMethod.POST)
	public ResponseEntity<?> checkUserCredentials(@RequestBody User user) {
		User userResponse = userRepo.findByUserEmailAndUserPassword(user.getUserEmail(), user.getUserPassword());
		if (userResponse == null) {
			return new ResponseEntity<>(new CustomErrorType("Please login with valid credentials!!"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
}
