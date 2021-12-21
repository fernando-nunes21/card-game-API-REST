package com.project.cardgame.unit.cards

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.dto.CardDTO
import com.project.cardgame.cards.dto.CardMapperImpl
import com.project.cardgame.exceptions.InvalidInputFieldException
import com.project.cardgame.exceptions.NotFoundException
import com.project.cardgame.cards.repository.CardRepository
import com.project.cardgame.cards.service.CardServiceImpl
import com.project.cardgame.handler.MessageResponse
import org.springframework.dao.EmptyResultDataAccessException
import spock.lang.Specification

class CardServiceTest extends Specification {

    private CardRepository cardRepository = Mock(CardRepository)
    private CardMapperImpl cardMapper = new CardMapperImpl()
    private CardServiceImpl cardService = new CardServiceImpl(cardRepository, cardMapper)

    def "Get cards should return a list of cards"() {
        Integer offset = 0
        Integer limit = 20
        List<CardDTO> expectedCardList = new ArrayList<>()

        given:
        this.cardRepository.getAll(offset, limit, null) >> expectedCardList

        when:
        List<CardDTO> cardList = this.cardService.getCards(offset, limit, null)

        then:
        cardList.size() == expectedCardList.size()
    }

    def "Get cards should return a list of cards search by name"() {
        Integer offset = 0
        Integer limit = 20
        String cardName = "Test"
        List<CardDTO> expectedCardList = new ArrayList<>()

        given:
        this.cardRepository.getAll(offset, limit, cardName) >> expectedCardList

        when:
        List<CardDTO> cardList = this.cardService.getCards(offset, limit, cardName)

        then:
        cardList.size() == expectedCardList.size()
    }

    def "Get card by id should return a card when card id exists"() {
        Integer cardId = 1
        Card card = new Card()

        given:
        this.cardRepository.getById(cardId) >> card

        when:
        CardDTO result = cardService.getCardById(cardId)

        then:
        result != null
    }

    def "Get card by id should throw exception when card id not exists"() {
        Integer cardId = 500

        given:
        this.cardRepository.getById(cardId) >> {
            throw new EmptyResultDataAccessException(1)
        }

        when:
        CardDTO result = cardService.getCardById(cardId)

        then:
        thrown(NotFoundException)
    }

    def "Create card should insert card when fields are valid"() {
        Integer cardId = 1
        CardDTO cardDTO = new CardDTO()
        cardDTO.name = "Test"
        cardDTO.typeCard = "Test"
        cardDTO.description = "Test description"
        Card card = cardMapper.convertToEntity(cardDTO)
        MessageResponse expectedMessage = new MessageResponse("Card ID: ${cardId} criada com sucesso!")

        given:
        this.cardRepository.insert(card) >> cardId

        when:
        MessageResponse messageResponse = this.cardService.createCard(cardDTO)

        then:
        expectedMessage.getResponse() == messageResponse.getResponse()
    }

    def "Create card should throw exception when exists some field invalid"() {
        CardDTO cardDTO = new CardDTO()
        cardDTO.name = "Test"
        cardDTO.typeCard = ""
        cardDTO.description = "Test description"

        given:

        when:
        MessageResponse messageResponse = this.cardService.createCard(cardDTO)

        then:
        thrown(InvalidInputFieldException)
    }

    def "Edit card should edit when fields are valid and card exists"() {
        Integer cardId = 1
        CardDTO cardDTO = new CardDTO()
        cardDTO.name = "Test"
        cardDTO.typeCard = "Test"
        cardDTO.description = "Test description"
        Card card = cardMapper.convertToEntity(cardDTO)
        MessageResponse expectedMessage = new MessageResponse("Card ID: ${cardId} editada com sucesso!")

        given:
        this.cardRepository.edit(cardId, card) >> cardId

        when:
        MessageResponse messageResponse = this.cardService.editCard(cardId, cardDTO)

        then:
        expectedMessage.getResponse() == messageResponse.getResponse()
    }

    def "Edit card should throw exception when card id not exist"() {
        Integer cardId = 500
        CardDTO cardDTO = new CardDTO()
        cardDTO.name = "Test"
        cardDTO.typeCard = "Test"
        cardDTO.description = "Test description"
        Card card = cardMapper.convertToEntity(cardDTO)

        given:
        this.cardRepository.edit(cardId, card) >> {
            throw new EmptyResultDataAccessException(1)
        }

        when:
        MessageResponse messageResponse = this.cardService.editCard(cardId, cardDTO)

        then:
        thrown(NotFoundException)
    }

    def "Edit card should throw exception when exists some field invalid"() {
        Integer cardId = 1
        CardDTO cardDTO = new CardDTO()
        cardDTO.name = "Test"
        cardDTO.typeCard = "Test"
        cardDTO.description = ""

        when:
        this.cardService.editCard(cardId, cardDTO)

        then:
        thrown(InvalidInputFieldException)
    }

    def "Delete card should delete when card id exist"() {
        Integer cardId = 1
        MessageResponse expectedMessage = new MessageResponse("Card ID: ${cardId} deletada com sucesso!")

        given:
        this.cardRepository.delete(cardId) >> cardId

        when:
        MessageResponse messageResponse = this.cardService.deleteCard(cardId)

        then:
        expectedMessage.getResponse() == messageResponse.getResponse()
    }

    def "Delete card should throw exception when card id not exist"() {
        Integer cardId = 500

        given:
        this.cardRepository.delete(cardId) >> {
            throw new EmptyResultDataAccessException(1)
        }

        when:
        MessageResponse messageResponse = this.cardService.deleteCard(cardId)

        then:
        thrown(NotFoundException)
    }
}
