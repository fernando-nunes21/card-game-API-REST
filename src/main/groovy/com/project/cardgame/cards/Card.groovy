package com.project.cardgame.cards

import groovy.transform.Canonical

@Canonical
class Card {
    private Integer id
    private String name
    private String typeCard
    private String description

    Card(){}

}
