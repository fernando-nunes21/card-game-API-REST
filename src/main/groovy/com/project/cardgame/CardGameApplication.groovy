package com.project.cardgame

import com.project.cardgame.cards.dto.CardMapperImpl
import com.project.cardgame.decks.dto.DeckMapperImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class CardGameApplication {

	@Autowired
	private JdbcTemplate jdbcTemplate

	@Bean
	CardMapperImpl cardMapper() {
		return new CardMapperImpl()
	}

	@Bean
	DeckMapperImpl deckMapper(){
		return new DeckMapperImpl()
	}

	static void main(String[] args) {
		SpringApplication.run(CardGameApplication, args)
	}
}
