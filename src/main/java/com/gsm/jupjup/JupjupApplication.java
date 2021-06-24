package com.gsm.jupjup;

import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.LaptopSpecRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@SpringBootApplication
@EnableJpaAuditing
public class JupjupApplication {

	public static void main(String[] args) {
		String PROPERTIES = "spring.config.location=classpath:/application.yml"
				+",classpath:/key.yml";
		new SpringApplicationBuilder(JupjupApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}
}
