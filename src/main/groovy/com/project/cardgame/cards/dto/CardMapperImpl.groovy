package com.project.cardgame.cards.dto

import com.project.cardgame.cards.Card

class CardMapperImpl implements CardMapper{

    @Override
    CardDTO convertToDTO(Card card) {
        CardDTO cardDTO = new CardDTO()
        cardDTO.id = card.id
        cardDTO.name = card.name
        cardDTO.typeCard = card.typeCard
        cardDTO.description = card.description
        return cardDTO
    }

    @Override
    Card convertToEntity(CardDTO cardDTO) {
        Card card = new Card()
        card.id = cardDTO.id
        card.name = cardDTO.name
        card.typeCard = cardDTO.typeCard
        card.description = cardDTO.description
        return card
    }
}
