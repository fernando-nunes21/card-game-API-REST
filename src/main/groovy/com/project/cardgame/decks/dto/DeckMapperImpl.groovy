package com.project.cardgame.decks.dto

import com.project.cardgame.decks.Deck

class DeckMapperImpl implements DeckMapper{

    @Override
    DeckDTO convertToDTO(Deck deck) {
        DeckDTO deckDTO = new DeckDTO()
        deckDTO.id = deck.id
        deckDTO.name = deck.name
        deckDTO.idCards = deck.idCards
        deckDTO.description = deck.description
        return deckDTO
    }

    @Override
    Deck convertToEntity(DeckDTO deckDTO) {
        Deck deck = new Deck()
        deck.id = deckDTO.id
        deck.name = deckDTO.name
        deck.idCards = deckDTO.idCards
        deck.description = deckDTO.description
        return deck
    }
}
