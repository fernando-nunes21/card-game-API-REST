package com.project.cardgame.cards.exceptions

class NotFoundCard extends RuntimeException {
    NotFoundCard(String error){
        super(error)
    }
}
