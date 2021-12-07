package com.project.cardgame.cards

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical

@Canonical
@JsonIgnoreProperties
class CardHeader {
    private String auth
}
