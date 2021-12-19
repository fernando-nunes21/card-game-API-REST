package com.project.cardgame.decks

import com.project.cardgame.cards.Card
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

class DeckRowMapper implements RowMapper<Deck> {
    @Override
    Deck mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Deck deck = new Deck()
        deck.id = resultSet.getInt("id")
        deck.name = resultSet.getString("name")
        String idCards = resultSet.getArray("id_cards")
        deck.idCards = convertStringToList(idCards)
        deck.description = resultSet.getString("description")
        return deck
    }

    private List<String> convertStringToList (String string){
        String result = string.replace('{', '').replace('}','')
        return new ArrayList<String>(Arrays.asList(result.split(",")))
    }
}
