package com.project.cardgame.decks.dto

import groovy.transform.Canonical

@Canonical
class DeckDTO {
    Integer id
    String name
    List<String> idCards
    String description

    DeckDTO(){}
}