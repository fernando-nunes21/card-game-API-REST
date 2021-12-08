package com.project.cardgame.cards.repository

import com.project.cardgame.cards.Card
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class CardRepository {

    private JdbcTemplate jdbcTemplate

    CardRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate
    }

    List<Card> getAll(Integer offset, Integer limit){

    }

    List<Card> getAllByName(Integer offset, Integer limit, String name){

    }

    Card getById(Integer id){

    }

    void insert(Card card){

    }

    void edit(Integer id, Card card){

    }

    void delete(Integer id){

    }
}
