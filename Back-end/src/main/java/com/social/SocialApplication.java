package com.social;

import com.social.config.SwaggerConfiguration;
import com.social.model.User;
import com.social.model.UserType;
import com.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class SocialApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}
	

/*
	@Override
	public void run(String... args) throws Exception {
		User user = User.builder()
				.userType(UserType.ADMIN)
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.email("admin@gmail.com")
				.created(Instant.now())
				.enabled(true)
				.completed(true)
				.verificationRequested(false)
				.verified(true)
				.build();
		userRepository.save(user);
	}
*/

}
