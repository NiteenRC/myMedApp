package com.fico.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fico.demo.model.User;
import com.fico.demo.repo.UserRepo;

@SpringBootApplication
public class SpringAppLauncher {

	public static void main(String[] args) {
		SpringApplication.run(SpringAppLauncher.class);
	}

	@Bean
	public CommandLineRunner setup(UserRepo userRepo) {
		return (args) -> {
			User user = new User();
			user.setUserId(1);
			user.setUserName("Niteen");
			user.setUserEmail("admin@gmail.com");
			user.setUserPassword("admin");
			user.setUserType("A");
			userRepo.save(user);
		};
	}
}