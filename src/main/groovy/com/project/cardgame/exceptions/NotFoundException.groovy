package com.project.cardgame.exceptions

class NotFoundException extends RuntimeException {
    NotFoundException(String error){
        super(error)
    }
}
