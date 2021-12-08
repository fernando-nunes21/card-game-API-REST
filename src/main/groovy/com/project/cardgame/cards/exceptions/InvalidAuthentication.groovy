package com.project.cardgame.cards.exceptions

class InvalidAuthentication extends RuntimeException{
    InvalidAuthentication(String error){
        super(error)
    }
}
