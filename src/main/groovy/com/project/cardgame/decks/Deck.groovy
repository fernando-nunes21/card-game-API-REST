package com.project.cardgame.decks

import groovy.transform.Canonical

@Canonical
class Deck {
    Integer id
    String name
    List<String> idCards
    String description

    Deck(){}
}
