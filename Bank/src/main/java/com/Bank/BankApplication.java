package com.Bank;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@Controller
public class BankApplication {
	@Autowired
	public HttpServletRequest request;


	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	UserRepository userRepository;

	public BankApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/loggedIn")
	public String loggedIn() {
		return "loggedIn";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/")
	public String HomePage() {
		return "home";
	}

	@PostMapping("/register")
	public String register(User user) {
		User found = userRepository.getUserByUsername(user.getUsername());
		if (found == null) {
			userRepository.save(user);
		} else {
			return "User already exists";
		}
		return "redirect:/";
	}

	@PostMapping("/login")
	public String login(User user,HttpSession session) {
		User found = userRepository.getUserByUsername(user.getUsername());
		if (found != null && found.getPassword().equals(user.getPassword())) {
			session.setAttribute("username", found.getUsername());

			return "redirect:/loggedIn";
		}
		else {return "redirect:/";}
	}

	@PostMapping("/deposit")
	public String deposit(@RequestParam("deposit") double depositAmount, User user) {

		HttpSession session = request.getSession();

		String username = (String) session.getAttribute("username");

		if (username != null) {
			User found = userRepository.getUserByUsername(username);
			if (found != null) {
				// Update the user's balance
				double currentBalance = found.getBalance();
				found.setBalance(currentBalance + depositAmount);

				// Save the updated user
				userRepository.save(found);
				return "redirect:/loggedIn";
			}
			return "redirect:/";
		}
        return "redirect:/";
    }

	@PostMapping("/withdraw")
	public String withdraw(@RequestParam("withdraw") double withdrawAmount, User user) {

		HttpSession session = request.getSession();

		String username = (String) session.getAttribute("username");

		if (username != null) {
			User found = userRepository.getUserByUsername(username);
			if (found != null) {
				// Update the user's balance
				double currentBalance = found.getBalance();
				found.setBalance(currentBalance - withdrawAmount);

				userRepository.save(found);
				return "redirect:/loggedIn";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}


}
