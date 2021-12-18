package com.project.cardgame.enums

enum ValidationType {

    ADMIN_ROUTE ("Admin")

    String value

    ValidationType(String value){
        this.value = value
    }

    public String getValue(){
        return value
    }

}