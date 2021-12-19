package com.project.cardgame.decks.service

import com.project.cardgame.decks.dto.DeckDTO
import com.project.cardgame.handler.MessageResponse

interface DeckService {

    List<DeckDTO> getDecks(Integer offset, Integer limit, String name)

    DeckDTO getDeckById(Integer id)

    MessageResponse createDeck(DeckDTO deckDTO)

    MessageResponse editDeck(Integer id, DeckDTO deckDTO)

    MessageResponse deleteDeck(Integer id)

}