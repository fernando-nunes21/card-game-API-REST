package com.project.cardgame.decks

import com.project.cardgame.cards.Card
import groovy.transform.Canonical

@Canonical
class Deck {
    private Integer id
    private String name
    private List<Card> cards
    private String description
}
