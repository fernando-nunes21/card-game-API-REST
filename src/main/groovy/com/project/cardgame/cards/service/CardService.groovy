package com.project.cardgame.cards.service

import com.project.cardgame.cards.dto.CardDTO
import com.project.cardgame.handler.MessageResponse

interface CardService {

    List<CardDTO> getCards(Integer offset, Integer limit, String name)

    CardDTO getCardById(Integer id)

    MessageResponse createCard(CardDTO cardDTO)

    MessageResponse editCard(Integer id, CardDTO cardDTO)

    MessageResponse deleteCard(Integer id)

}