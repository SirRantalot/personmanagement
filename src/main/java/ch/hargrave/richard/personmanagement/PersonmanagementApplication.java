package ch.hargrave.richard.personmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PersonmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonmanagementApplication.class, args);
	}

}
