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
public class JupjupApplication implements CommandLineRunner {

	private final LaptopSpecRepo laptopSpecRepo;

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties";

	public static void main(String[] args) {
//		SpringApplication
//				.run(JupjupApplication.class, args);
		new SpringApplicationBuilder(JupjupApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		final List<LaptopSpec> laptopSpecDomainList = Arrays.asList(
				LaptopSpec.builder()
						.CPU("intel-i7")
						.GPU("LadeonPro5600")
						.RAM("16gb")
						.SSD("256gb")
						.HDD("1tb")
						.build(),
				LaptopSpec.builder()
						.CPU("intel-i9")
						.GPU("Pro3455")
						.RAM("15gb")
						.SSD("356gb")
						.HDD("1tb")
						.build(),
				LaptopSpec.builder()
						.CPU("intel-i9")
						.GPU("sexy")
						.RAM("256gb")
						.SSD("1pb")
						.HDD("1tb")
						.build());
	}
}
