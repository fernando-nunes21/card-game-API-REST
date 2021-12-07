package com.project.cardgame.cards.exceptions

class LimitInvalidException extends RuntimeException{
    LimitInvalidException(String error){
        super(error)
    }
}
