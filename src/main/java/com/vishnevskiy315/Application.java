package com.vishnevskiy315;

import com.vishnevskiy315.model.User;
import com.vishnevskiy315.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	UserServiceImpl userService;


	public static void main(String[] args) {

		UserServiceImpl userService = new UserServiceImpl();
		String SessionID = userService.getAllUsers()
				.getHeaders()
				.getValuesAsList("set-cookie")
				.get(0);

		User addUser = new User(3L, "James", "Brown", (byte) 30);
		userService.addUser(addUser, SessionID);

		User user = new User(3L, "Thomas", "Shelby", (byte) 25);
		userService.editUser(user, SessionID);

		userService.deleteUser(user, SessionID);
	}
}
