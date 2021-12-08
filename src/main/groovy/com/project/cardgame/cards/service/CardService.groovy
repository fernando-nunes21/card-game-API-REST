package com.project.cardgame.cards.service

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.CardHeader

interface CardService {

    List<Card> getCards(Integer offset, Integer Limit, String name)

    Card getCardById(Integer id)

    void createCard(CardHeader cardHeader, Card card)

    void editCard(CardHeader cardHeader, Integer id, Card card)

    void deleteCard(CardHeader cardHeader, Integer id)

}