package com.project.cardgame.interceptor

import com.project.cardgame.annotations.AdminRouteAuth
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Slf4j
class Interceptor implements HandlerInterceptor{

    private final String authorization = "cardAdminAPI"

    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        AdminRouteAuth adminRouteAuth = ((HandlerMethod)handler).getMethod().getAnnotation((AdminRouteAuth.class))

        if(adminRouteAuth != null){
            log.info("Logando evento de autenticação administrador")
            if(request.getHeader("Authorization") == authorization) {
                return true
            } else {
                response.status = 401
                return false
            }
        } else {
            return true
        }
    }
}
