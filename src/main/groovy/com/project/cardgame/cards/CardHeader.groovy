package com.project.cardgame.cards

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
@JsonIgnoreProperties
class CardHeader {

    CardHeader(){}

    @JsonProperty("Auth")
    String auth
}
