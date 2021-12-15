package com.project.cardgame.cards

import groovy.transform.Canonical

@Canonical
class Card {

    Integer id
    String name
    String typeCard
    String description

    Card(){}
}
