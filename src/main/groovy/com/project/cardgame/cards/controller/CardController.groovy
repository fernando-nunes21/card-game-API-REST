package com.project.cardgame.cards.controller

import com.project.cardgame.annotations.AdminRouteAuth
import com.project.cardgame.cards.dto.CardDTO
import com.project.cardgame.cards.exceptions.InputEmptyField
import com.project.cardgame.cards.exceptions.NotFoundCard
import com.project.cardgame.exceptions.LimitInvalidException

import com.project.cardgame.cards.service.CardService
import com.project.cardgame.handler.ErrorDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/cards")
class CardController {

    private CardService cardService

    CardController(CardService cardService) {
        this.cardService = cardService
    }

    @GetMapping
    ResponseEntity getCards(@RequestParam Integer offset,
                            @RequestParam Integer limit,
                            @RequestParam(required = false) String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.cardService.getCards(offset, limit, name))
        } catch (LimitInvalidException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(error.getMessage()))
        }
    }

    @GetMapping("/{id}")
    ResponseEntity getCardById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.cardService.getCardById(id))
        } catch (NotFoundCard error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(error.getMessage()))
        }
    }

    @AdminRouteAuth
    @PostMapping
    ResponseEntity createCard(@RequestBody CardDTO cardDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.cardService.createCard(cardDTO))
        } catch (InputEmptyField error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(error.getMessage()))
        }
    }

    @AdminRouteAuth
    @PutMapping("/{id}")
    ResponseEntity editCard(@PathVariable Integer id,
                            @RequestBody CardDTO cardDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.cardService.editCard(id, cardDTO))
        } catch (NotFoundCard error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(error.getMessage()))
        } catch (InputEmptyField error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(error.getMessage()))
        }
    }

    @AdminRouteAuth
    @DeleteMapping("/{id}")
    ResponseEntity deleteCard(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.cardService.deleteCard(id))
        } catch (NotFoundCard error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(error.getMessage()))
        }
    }

}
