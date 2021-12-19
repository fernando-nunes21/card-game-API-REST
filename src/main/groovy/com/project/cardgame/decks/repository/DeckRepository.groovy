package com.project.cardgame.decks.repository

import com.project.cardgame.decks.Deck
import com.project.cardgame.decks.DeckRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import java.sql.Array
import java.sql.Types

@Service
class DeckRepository {

    private JdbcTemplate jdbcTemplate

    DeckRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate
    }

    List<Deck> getAll(Integer offset, Integer limit, String name) {
        String sql = "SELECT * FROM decks WHERE 1 = 1 ${name ? "AND name LIKE '${name}'" : ""} OFFSET ? LIMIT ?"
        Object[] params = new Object[]{
                offset,
                limit
        }
        return jdbcTemplate.query(sql, new DeckRowMapper(), params)
    }

    Deck getById(Integer id) throws Exception {
        String sql = "SELECT * FROM decks WHERE id = ?"
        Object[] params = new Object[]{
                id
        }
        return jdbcTemplate.queryForObject(sql, new DeckRowMapper(), params)
    }

    Integer insert(Deck deck) {
        String sql = "INSERT INTO decks (name, id_cards, description) VALUES (?, ?, ?) RETURNING id"
        Object[] params = new Object[]{
                deck.name,
                createSqlArray(deck.idCards),
                deck.description
        }
        return jdbcTemplate.queryForObject(sql, Integer.class, params)
    }

    Integer edit(Integer id, Deck deck) {
        String sql = "UPDATE decks SET name = ?, id_cards = ?, description = ? WHERE id = ? RETURNING id"
        Object[] params = new Object[]{
                deck.name,
                createSqlArray(deck.idCards),
                deck.description,
                id
        }
        return jdbcTemplate.queryForObject(sql, Integer.class, params)
    }

    Integer delete(Integer id) {
        String sql = "DELETE FROM decks WHERE id = ? RETURNING id"
        Object[] params = new Object[]{
                id
        }
        return jdbcTemplate.queryForObject(sql, Integer.class, params)
    }

    private Array createSqlArray(List<Integer> list){
        return jdbcTemplate.getDataSource().getConnection().createArrayOf("integer", list.toArray())
    }
}
