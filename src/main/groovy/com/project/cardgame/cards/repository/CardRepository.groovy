package com.project.cardgame.cards.repository

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.CardRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class CardRepository {

    private JdbcTemplate jdbcTemplate

    CardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate
    }

    List<Card> getAll(Integer offset, Integer limit, String name) {
        String sql = "SELECT * FROM cards WHERE 1 = 1 ${name ? "AND name LIKE '${name}'" : ""} OFFSET ? LIMIT ?"
        Object[] params = new Object[]{
                offset,
                limit
        }
        return jdbcTemplate.query(sql, new CardRowMapper(), params)
    }

    Card getById(Integer id) throws Exception {
        String sql = "SELECT * FROM cards WHERE id = ?"
        Object[] params = new Object[]{
            id
        }
        return jdbcTemplate.queryForObject(sql, new CardRowMapper(), params)
    }

    Integer insert(Card card) {
        String sql = "INSERT INTO cards (name, type_card, description) VALUES (?, ?, ?) RETURNING id"
        Object[] params = new Object[]{
            card.name,
            card.typeCard,
            card.description
        }
        return jdbcTemplate.queryForObject(sql, Integer.class, params)
    }

    Integer edit(Integer id, Card card) {
        String sql = "UPDATE cards SET name = ?, type_card = ?, description = ? WHERE id = ? RETURNING id"
        Object[] params = new Object[]{
                card.name,
                card.typeCard,
                card.description,
                id
        }
        return jdbcTemplate.queryForObject(sql, Integer.class, params)
    }

    Integer delete(Integer id) {
        String sql = "DELETE FROM cards WHERE id = ? RETURNING id"
        Object[] params = new Object[]{
            id
        }
        return jdbcTemplate.queryForObject(sql, Integer.class, params)
    }
}
