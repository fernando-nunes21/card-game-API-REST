package com.project.cardgame.cards.service

import com.project.cardgame.cards.dto.CardDTO
import com.project.cardgame.handler.MessageResponse

interface CardService {

    List<CardDTO> getCards(Integer offset, Integer Limit, String name)

    CardDTO getCardById(Integer id)

    MessageResponse createCard(String auth, CardDTO card)

    MessageResponse editCard(String auth, Integer id, CardDTO card)

    MessageResponse deleteCard(String auth, Integer id)

}