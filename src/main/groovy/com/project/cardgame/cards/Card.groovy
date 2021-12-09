package com.project.cardgame.cards

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class Card {

    Integer id
    String name

    //TODO - alterar isso aqui no modelo do projeto e aqui
    @JsonProperty("typecard")
    String typeCard
    String description

    Card(){}


}
