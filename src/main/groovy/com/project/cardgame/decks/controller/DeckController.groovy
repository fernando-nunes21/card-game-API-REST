package com.project.cardgame.decks.controller

import com.project.cardgame.cards.exceptions.InputEmptyField
import com.project.cardgame.cards.exceptions.NotFoundCard
import com.project.cardgame.decks.dto.DeckDTO
import com.project.cardgame.decks.service.DeckServiceImpl
import com.project.cardgame.exceptions.LimitInvalidException
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
@RequestMapping("/v1/decks")
class DeckController {

    private DeckServiceImpl deckService

    DeckController(DeckServiceImpl deckService) {
        this.deckService = deckService
    }

    @GetMapping
    ResponseEntity getDecks(@RequestParam Integer offset,
                            @RequestParam Integer limit,
                            @RequestParam(required = false) String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.deckService.getDecks(offset, limit, name))
        } catch (LimitInvalidException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(error.getMessage()))
        }
    }

    @GetMapping("/{id}")
    ResponseEntity getDeckById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.deckService.getDeckById(id))
        } catch (NotFoundCard error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(error.getMessage()))
        }
    }

    @PostMapping
    ResponseEntity createDeck(@RequestBody DeckDTO deckDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.deckService.createDeck(deckDTO))
        } catch (InputEmptyField error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(error.getMessage()))
        }
    }

    @PutMapping("/{id}")
    ResponseEntity editDeck(@PathVariable Integer id, @RequestBody DeckDTO deckDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.deckService.editDeck(id, deckDTO))
        } catch (NotFoundCard error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(error.getMessage()))
        } catch (InputEmptyField error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(error.getMessage()))
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteDeck(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.deckService.deleteDeck(id))
        } catch (NotFoundCard error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(error.getMessage()))
        }
    }
}
