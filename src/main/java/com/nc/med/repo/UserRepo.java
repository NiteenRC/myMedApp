package com.nc.med.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nc.med.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByUserName(String user);

	User findByUserEmail(String userEmail);

	User findByUserEmailAndUserPassword(String userName, String userPassword);
}
