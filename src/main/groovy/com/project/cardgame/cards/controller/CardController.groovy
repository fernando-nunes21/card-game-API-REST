package com.project.cardgame.cards.controller

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.exceptions.InputEmptyField
import com.project.cardgame.cards.exceptions.InvalidAuthentication
import com.project.cardgame.cards.exceptions.NotFoundCard
import com.project.cardgame.cards.exceptions.NotFoundCards
import com.project.cardgame.exceptions.LimitInvalidException

import com.project.cardgame.cards.service.CardService
import com.project.cardgame.handler.ErrorDetails
import com.project.cardgame.handler.ResponseDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingRequestHeaderException
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
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver


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
            //Todo - Desacoplar o retorno da service
            List<Card> cards = this.cardService.getCards(offset, limit, name)
            return new ResponseEntity(cards, HttpStatus.OK)
            //TODO - Busca vazio não é tão ruim quanto eu achava.
        } catch (NotFoundCards error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.NOT_FOUND)
        } catch (LimitInvalidException error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{id}")
    ResponseEntity getCardById(@PathVariable Integer id) {
        try {
            Card card = this.cardService.getCardById(id)
            return new ResponseEntity(card, HttpStatus.OK)
        } catch (NotFoundCard error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    ResponseEntity createCard(@RequestHeader("auth") String auth, @RequestBody Card card) {
        try {
            this.cardService.createCard(auth, card)
            return new ResponseEntity(new ResponseDetails("Card criado com sucesso!"), HttpStatus.CREATED)
        } catch (InvalidAuthentication error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.UNAUTHORIZED)
        } catch (InputEmptyField error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/{id}")
    ResponseEntity editCard(@PathVariable Integer id,
                            @RequestHeader("auth") String auth,
                            @RequestBody Card card) {
        try {
            this.cardService.editCard(auth, id, card)
            return new ResponseEntity(new ResponseDetails("Card editado com sucesso!"), HttpStatus.OK)
        } catch (InvalidAuthentication error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.UNAUTHORIZED)
        } catch (NotFoundCard error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.NOT_FOUND)
        } catch (InputEmptyField error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteCard(@PathVariable Integer id, @RequestHeader("auth") String auth) {
        try {
            this.cardService.deleteCard(auth, id)
            return new ResponseEntity(new ResponseDetails("Card deletado com sucesso!"), HttpStatus.OK)
        } catch (InvalidAuthentication error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.UNAUTHORIZED)
        } catch (NotFoundCard error) {
            return new ResponseEntity(new ErrorDetails(error.getMessage()), HttpStatus.NOT_FOUND)
        }
    }


}
