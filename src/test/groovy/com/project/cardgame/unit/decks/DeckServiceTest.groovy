package com.project.cardgame.unit.decks

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.dto.CardDTO
import com.project.cardgame.exceptions.InvalidInputFieldException
import com.project.cardgame.exceptions.NotFoundException
import com.project.cardgame.decks.Deck
import com.project.cardgame.decks.dto.DeckDTO
import com.project.cardgame.decks.dto.DeckMapperImpl
import com.project.cardgame.decks.repository.DeckRepository
import com.project.cardgame.decks.service.DeckServiceImpl
import com.project.cardgame.handler.MessageResponse
import org.springframework.dao.EmptyResultDataAccessException
import spock.lang.Specification

class DeckServiceTest extends Specification {

    private DeckRepository deckRepository = Mock(DeckRepository)
    private DeckMapperImpl deckMapper = new DeckMapperImpl()
    private DeckServiceImpl deckService = new DeckServiceImpl(deckRepository, deckMapper)

    def "Get decks should return a list of decks"() {
        Integer offset = 0
        Integer limit = 20
        List<DeckDTO> expectedDeckList = new ArrayList<>()

        given:
        this.deckRepository.getAll(offset, limit, null) >> expectedDeckList

        when:
        List<DeckDTO> deckList = this.deckService.getDecks(offset, limit, null)

        then:
        deckList.size() == expectedDeckList.size()
    }

    def "Get decks should return a list of decks search by name"() {
        Integer offset = 0
        Integer limit = 20
        String deckName = "Test"
        List<DeckDTO> expectedDeckList = new ArrayList<>()

        given:
        this.deckRepository.getAll(offset, limit, deckName) >> expectedDeckList

        when:
        List<DeckDTO> deckList = this.deckService.getDecks(offset, limit, deckName)

        then:
        deckList.size() == expectedDeckList.size()
    }

    def "Get deck by id should return a deck when deck id exists"() {
        Integer deckId = 1
        Deck deck = new Deck()

        given:
        this.deckRepository.getById(deckId) >> deck

        when:
        DeckDTO result = deckService.getDeckById(deckId)

        then:
        result != null
    }

    def "Get deck by id should throw exception when deck id not exists"() {
        Integer deckId = 500

        given:
        this.deckRepository.getById(deckId) >> {
            throw new EmptyResultDataAccessException(1)
        }

        when:
        DeckDTO result = deckService.getDeckById(deckId)

        then:
        thrown(NotFoundException)
    }

    def "Create deck should insert deck when fields are valid"() {
        Integer deckId = 1
        DeckDTO deckDTO = new DeckDTO()
        deckDTO.name = "Test"
        List<String> idCards = new ArrayList<>()
        idCards.add("1")
        deckDTO.idCards = idCards
        deckDTO.description = "Test description"
        Deck deck = deckMapper.convertToEntity(deckDTO)
        MessageResponse expectedMessage = new MessageResponse("Deck ID: ${deckId} criado com sucesso!")

        given:
        this.deckRepository.insert(deck) >> deckId

        when:
        MessageResponse messageResponse = this.deckService.createDeck(deckDTO)

        then:
        expectedMessage.getResponse() == messageResponse.getResponse()
    }

    def "Create deck should throw exception when exists some field invalid"() {
        DeckDTO deckDTO = new DeckDTO()
        deckDTO.name = ""
        List<String> idCards = new ArrayList<>()
        idCards.add("1")
        deckDTO.idCards = idCards
        deckDTO.description = "Test description"

        when:
        MessageResponse messageResponse = this.deckService.createDeck(deckDTO)

        then:
        thrown(InvalidInputFieldException)
    }

    def "Edit card should edit when fields are valid and card exists"() {
        Integer deckId = 1
        DeckDTO deckDTO = new DeckDTO()
        deckDTO.name = "Test"
        List<String> idCards = new ArrayList<>()
        idCards.add("1")
        deckDTO.idCards = idCards
        deckDTO.description = "Test description"
        Deck deck = deckMapper.convertToEntity(deckDTO)
        MessageResponse expectedMessage = new MessageResponse("Deck ID: ${deckId} editado com sucesso!")

        given:
        this.deckRepository.edit(deckId, deck) >> deckId

        when:
        MessageResponse messageResponse = this.deckService.editDeck(deckId, deckDTO)

        then:
        expectedMessage.getResponse() == messageResponse.getResponse()
    }

    def "Edit deck should throw exception when deck id not exist"() {
        Integer deckId = 500
        DeckDTO deckDTO = new DeckDTO()
        deckDTO.name = "Test"
        List<String> idCards = new ArrayList<>()
        idCards.add("1")
        deckDTO.idCards = idCards
        deckDTO.description = "Test description"
        Deck deck = deckMapper.convertToEntity(deckDTO)

        given:
        this.deckRepository.edit(deckId, deck) >> {
            throw new EmptyResultDataAccessException(1)
        }

        when:
        MessageResponse messageResponse = this.deckService.editDeck(deckId, deckDTO)

        then:
        thrown(NotFoundException)
    }

    def "Edit deck should throw exception when exists some field invalid"() {
        Integer deckId = 1
        DeckDTO deckDTO = new DeckDTO()
        deckDTO.name = "Test"
        deckDTO.description = ""

        when:
        this.deckService.editDeck(deckId, deckDTO)

        then:
        thrown(InvalidInputFieldException)
    }

    def "Delete deck should delete when deck id exist"() {
        Integer deckId = 1
        MessageResponse expectedMessage = new MessageResponse("Deck ID: ${deckId} deletado com sucesso!")

        given:
        this.deckRepository.delete(deckId) >> deckId

        when:
        MessageResponse messageResponse = this.deckService.deleteDeck(deckId)

        then:
        expectedMessage.getResponse() == messageResponse.getResponse()
    }

    def "Delete deck should throw exception when deck id not exist"() {
        Integer deckId = 500

        given:
        this.deckRepository.delete(deckId) >> {
            throw new EmptyResultDataAccessException(1)
        }

        when:
        MessageResponse messageResponse = this.deckService.deleteDeck(deckId)

        then:
        thrown(NotFoundException)
    }
}
