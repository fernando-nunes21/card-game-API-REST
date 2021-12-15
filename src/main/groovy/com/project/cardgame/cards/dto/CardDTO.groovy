package com.project.cardgame.cards.dto

import groovy.transform.Canonical

@Canonical
class CardDTO {

    Integer id
    String name
    String typeCard
    String description

    CardDTO(){}
}
