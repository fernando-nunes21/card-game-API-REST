package com.project.cardgame.cards

interface CardService {

    List<Card> getCards(Integer offset, Integer Limit, String name)

    Card getCardById(Integer id)

    void createCard(Card card)

    void editCard(Integer id, Card card)

    void deleteCard(Integer id)

}