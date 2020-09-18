package com.nc.med;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.nc.med.mail.MailService;
import com.nc.med.model.Category;
import com.nc.med.model.Product;
import com.nc.med.model.User;
import com.nc.med.repo.CategoryRepo;
import com.nc.med.repo.ProductRepo;
import com.nc.med.repo.UserRepo;

@SpringBootApplication
@EnableScheduling
public class SpringAppLauncher extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringAppLauncher.class);
	}
	
	@Autowired
	MailService emailService;

	@Bean
	public CommandLineRunner setup(UserRepo userRepo) {
		return (args) -> {
			//emailService.sendMail("niteen2010@gmail.com", "Test Subject", "TestMessage");
			User user = new User();
			user.setUserId(1);
			user.setUserName("Admin");
			user.setUserEmail("admin@gmail.com");
			user.setUserPassword("admin");
			user.setUserType("A");
			userRepo.save(user);
		};
	}

	@Bean
	public CommandLineRunner setupProduct(ProductRepo productRepo, CategoryRepo categoryRepo) {
		return (args) -> {
			mapCategoryFileData(categoryRepo, "C:\\Users\\administator\\Downloads\\h2database\\category.txt");
			mapProductFileData(productRepo, categoryRepo,
					"C:\\Users\\administator\\Downloads\\h2database\\product.txt");
		};
	}

	private static void mapCategoryFileData(CategoryRepo categoryRepo, String fileName) {
		if (Files.exists(Paths.get(fileName))) {
			try {
				List<Category> products = Files.lines(Paths.get(fileName)).skip(1).map(line -> {
					String[] result = line.split(",");
					try {
						if (categoryRepo.findByCategoryName(result[2].replaceAll("\"", "")) == null) {
							return new Category(result[1].replaceAll("\"", ""), result[2].replaceAll("\"", ""),
									new SimpleDateFormat("yyyy-MM-dd")
											.parse(result[3].replaceAll("00:00.0", "").replaceAll("\"", "")));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;

				}).collect(Collectors.toList());
				categoryRepo.saveAll(products);
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

	private static void mapProductFileData(ProductRepo productRepo, CategoryRepo categoryRepo, String fileName) {
		if (Files.exists(Paths.get(fileName))) {
			try {
				List<Product> products = Files.lines(Paths.get(fileName)).skip(1).map(line -> {
					String[] result = line.split(",");
					try {
						if (productRepo.findByProductName(result[4].replaceAll("\"", "")) == null) {
							return new Product(
									new SimpleDateFormat("yyyy-MM-dd")
											.parse(result[1].replaceAll("00:00.0", "").replaceAll("\"", "")),
									Double.valueOf(result[2].replaceAll("\"", "")), result[3].replaceAll("\"", ""),
									result[4].replaceAll("\"", ""), Integer.valueOf(result[5].replaceAll("\"", "")),
									categoryRepo.findById(Integer.valueOf(result[6].replaceAll("\"", ""))).get());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());
				productRepo.saveAll(products);
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringAppLauncher.class);
	}
}