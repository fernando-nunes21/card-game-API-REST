package com.project.cardgame

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class CardGameApplication {

	@Autowired
	private JdbcTemplate jdbcTemplate

	static void main(String[] args) {
		SpringApplication.run(CardGameApplication, args)
	}

}
