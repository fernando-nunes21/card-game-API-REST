package com.project.cardgame.cards

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


    @GetMapping
    ResponseEntity<?> getCards(@RequestParam Integer offset,
                               @RequestParam Integer limit,
                               @RequestParam(required = false) String name) {
        return new ResponseEntity<>(HttpStatus.OK)
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getCardById(@PathVariable Integer id) {
        return new ResponseEntity<>(HttpStatus.OK)
    }

    @PostMapping
    ResponseEntity<?> createCard(@RequestHeader CardHeader cardHeader, @RequestBody Card card) {
        return new ResponseEntity<>(HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    ResponseEntity<?> editCard(@PathVariable Integer id,
                               @RequestHeader CardHeader cardHeader,
                               @RequestBody Card card) {
        return new ResponseEntity<>(HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCard(@PathVariable Integer id, @RequestHeader CardHeader cardHeader) {
        return new ResponseEntity<>(HttpStatus.OK)
    }


}
