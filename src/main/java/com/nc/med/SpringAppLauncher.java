package com.nc.med;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.nc.med.model.User;
import com.nc.med.repo.UserRepo;

@SpringBootApplication
public class SpringAppLauncher extends SpringBootServletInitializer{

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
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(SpringAppLauncher.class);
	    }
}