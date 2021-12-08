package com.project.cardgame.cards

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class Card {

    Integer id
    String name

    @JsonProperty("typecard")
    String typeCard
    String description

    Card(){}


}
