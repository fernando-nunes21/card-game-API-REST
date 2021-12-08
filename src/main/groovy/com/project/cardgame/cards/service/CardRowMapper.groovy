package com.project.cardgame.cards.service

import com.project.cardgame.cards.Card
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

class CardRowMapper implements RowMapper<Card> {
    Card mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Card card = new Card()
        card.id = resultSet.getInt("id")
        card.name = resultSet.getString("name")
        card.typeCard = resultSet.getString("typecard")
        card.description = resultSet.getString("description")
        return card
    }
}
