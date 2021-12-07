package com.project.cardgame.decks

import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
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

    @GetMapping
    ResponseEntity<?> getDecks(@RequestParam Integer offset,
                               @RequestParam Integer limit,
                               @RequestParam(required = false) String name){
        return new ResponseEntity<>(HttpStatus.OK)
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getDeckById(@PathVariable Integer id){
        return new ResponseEntity<>(HttpStatus.OK)
    }

    @PostMapping
    ResponseEntity<?> createDeck(@RequestBody Deck deck){
        return new ResponseEntity<>(HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    ResponseEntity<?> editDeck(@PathVariable Integer id, @RequestBody Deck deck){
        return new ResponseEntity<>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    RequestEntity<?> deleteDeck(@PathVariable Integer id){
        return new ResponseEntity<>(HttpStatus.OK)
    }
}
