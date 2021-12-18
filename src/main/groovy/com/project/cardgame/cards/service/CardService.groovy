package com.project.cardgame.cards.service

import com.project.cardgame.cards.dto.CardDTO
import com.project.cardgame.handler.MessageResponse

interface CardService {

    List<CardDTO> getCards(Integer offset, Integer Limit, String name)

    CardDTO getCardById(Integer id)

    MessageResponse createCard(CardDTO card)

    MessageResponse editCard(Integer id, CardDTO card)

    MessageResponse deleteCard(Integer id)

}