package ar.com.demo.reba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ar.com.demo.reba"})
public class RebaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RebaApplication.class, args);
	}

}
