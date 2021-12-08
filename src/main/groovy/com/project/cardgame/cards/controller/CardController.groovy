package com.project.cardgame.cards.controller

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.CardHeader
import com.project.cardgame.cards.exceptions.NotFoundCard
import com.project.cardgame.cards.exceptions.NotFoundCards
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
import org.springframework.web.bind.annotation.RequestHeader
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
            List<Card> cards = this.cardService.getCards(offset, limit, name)
            return new ResponseEntity(cards, HttpStatus.OK)
        } catch (NotFoundCards error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.NOT_FOUND)
        } catch (LimitInvalidException error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{id}")
    ResponseEntity getCardById(@PathVariable Integer id) {
        try{
            Card card = this.cardService.getCardById(id)
            return new ResponseEntity(card, HttpStatus.OK)
        } catch (NotFoundCard error){
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.OK)
        }
    }

    @PostMapping
    ResponseEntity createCard(@RequestHeader CardHeader cardHeader, @RequestBody Card card) {
        this.cardService.createCard(cardHeader, card)
        return new ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    ResponseEntity editCard(@PathVariable Integer id,
                               @RequestHeader CardHeader cardHeader,
                               @RequestBody Card card) {
        return new ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteCard(@PathVariable Integer id, @RequestHeader CardHeader cardHeader) {
        return new ResponseEntity(HttpStatus.OK)
    }


}
