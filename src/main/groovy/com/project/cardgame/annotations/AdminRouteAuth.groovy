package com.project.cardgame.annotations

import com.project.cardgame.enums.ValidationType

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target( ElementType.METHOD)
@Retention( RetentionPolicy.RUNTIME)
@interface AdminRouteAuth {

    ValidationType validationType() default ValidationType.ADMIN_ROUTE

}