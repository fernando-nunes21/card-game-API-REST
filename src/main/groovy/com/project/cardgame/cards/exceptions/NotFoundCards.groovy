package com.project.cardgame.cards.exceptions

class NotFoundCards extends  RuntimeException{
    NotFoundCards(String error){
        super(error)
    }
}
