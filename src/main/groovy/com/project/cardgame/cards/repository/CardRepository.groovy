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

    List<Card> getAll(Integer offset, Integer limit) {
        String sql = "SELECT * FROM cards WHERE 1 = 1 OFFSET ? LIMIT ?"
        Object[] params = new Object[]{
                offset,
                limit
        }
        return jdbcTemplate.query(sql, new CardRowMapper(), params)
    }

    List<Card> getAllByName(Integer offset, Integer limit, String name) {
        String sql = "SELECT * FROM cards WHERE 1 = 1 AND name LIKE ? OFFSET ? LIMIT ?"
        Object[] params = new Object[]{
                name,
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

    void insert(Card card) {
        String sql = "INSERT INTO cards (name, typecard, description) VALUES (?, ?, ?)"
        Object[] params = new Object[]{
            card.name,
            card.typeCard,
            card.description
        }
        jdbcTemplate.update(sql, params)
    }

    void edit(Integer id, Card card) {
        String sql = "UPDATE cards SET name = ?, typecard = ?, description = ? WHERE id = ?"
        Object[] params = new Object[]{
                card.name,
                card.typeCard,
                card.description,
                id
        }
        jdbcTemplate.update(sql, params)
    }

    void delete(Integer id) {
        String sql = "DELETE FROM cards WHERE id = ?"
        Object[] params = new Object[]{
            id
        }
        jdbcTemplate.update(sql, params)
    }
}
