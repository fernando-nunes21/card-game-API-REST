package com.project.cardgame.decks.dto

import com.project.cardgame.decks.Deck

interface DeckMapper {

    DeckDTO convertToDTO(Deck deck)

    Deck convertToEntity(DeckDTO deckDTO)

}