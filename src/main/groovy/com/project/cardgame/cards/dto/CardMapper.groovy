package com.project.cardgame.cards.dto

import com.project.cardgame.cards.Card

interface CardMapper {

    CardDTO convertToDTO(Card card)

    Card convertToEntity(CardDTO cardDTO)
}