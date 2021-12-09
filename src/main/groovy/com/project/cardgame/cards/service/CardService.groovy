package com.project.cardgame.cards.service

import com.project.cardgame.cards.Card

interface CardService {

    List<Card> getCards(Integer offset, Integer Limit, String name)

    Card getCardById(Integer id)

    void createCard(String auth, Card card)

    void editCard(String auth, Integer id, Card card)

    void deleteCard(String auth, Integer id)

}