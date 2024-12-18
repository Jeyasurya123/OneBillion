package com.example.OneBillion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OneBillionApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneBillionApplication.class, args);
	}

}
