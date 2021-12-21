package com.project.cardgame.exceptions

class InvalidInputFieldException extends RuntimeException {
    InvalidInputFieldException(String error){
        super(error)
    }
}
