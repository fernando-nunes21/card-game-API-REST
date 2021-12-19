package com.project.cardgame.decks.service

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.exceptions.InputEmptyField
import com.project.cardgame.cards.exceptions.NotFoundCard
import com.project.cardgame.decks.Deck
import com.project.cardgame.decks.dto.DeckDTO
import com.project.cardgame.decks.dto.DeckMapperImpl
import com.project.cardgame.decks.repository.DeckRepository
import com.project.cardgame.exceptions.LimitInvalidException
import com.project.cardgame.handler.MessageResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class DeckServiceImpl implements DeckService {

    private final DEFAULT_LIMIT = 30

    private final DeckRepository deckRepository
    private final DeckMapperImpl deckMapper

    DeckServiceImpl(DeckRepository deckRepository, DeckMapperImpl deckMapper) {
        this.deckRepository = deckRepository
        this.deckMapper = deckMapper
    }

    @Override
    List<DeckDTO> getDecks(Integer offset, Integer limit, String name) {
        validateLimitField(limit)
        List<Deck> decks = deckRepository.getAll(offset, limit, name)
        return decks.stream()
                .map(deckMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    DeckDTO getDeckById(Integer id) {
        try {
            return deckMapper.convertToDTO(this.deckRepository.getById(id))
        } catch (EmptyResultDataAccessException ignored) {
            throw new NotFoundCard("Nao foi encontrado o baralho com id: ${id}")
        }
    }

    @Override
    MessageResponse createDeck(DeckDTO deckDTO) {
        validateInputDeckFields(deckDTO)
        Integer deckId = deckRepository.insert(deckMapper.convertToEntity(deckDTO))
        return new MessageResponse("Deck ID: ${deckId} criado com sucesso!")
    }

    @Override
    MessageResponse editDeck(Integer id, DeckDTO deckDTO) {
        validateInputDeckFields(deckDTO)
        try{
            Integer deckEditedId = deckRepository.edit(id, deckMapper.convertToEntity(deckDTO))
            return new MessageResponse("Deck ID: ${deckEditedId} editado com sucesso!")
        } catch (EmptyResultDataAccessException ignored){
            throw new NotFoundCard("Nao foi encontrada o baralho com id: ${id}")
        }
    }

    @Override
    MessageResponse deleteDeck(Integer id) {
        try{
            Integer deckDeletedId = deckRepository.delete(id)
            return new MessageResponse("Deck ID: ${deckDeletedId} deletado com sucesso!")
        } catch (EmptyResultDataAccessException ignored){
            throw new NotFoundCard("Nao foi encontrada o baralho com id: ${id}")
        }
    }

    private void validateInputDeckFields(DeckDTO deckDTO) {
        if (isFieldEmpty(deckDTO.name)) {
            throw new InputEmptyField("O campo do nome está vazio ou é nulo")
        }
        if (isFieldEmpty(deckDTO.idCards)) {
            throw new InputEmptyField("O campo de cards está vazio ou é nulo")
        }
        if (isFieldEmpty(deckDTO.description)) {
            throw new InputEmptyField("O campo de descrição do card está vazio ou é nulo")
        }
    }

    private boolean isFieldEmpty(String name) {
        if (name == null) {
            return true
        } else {
            return name.equalsIgnoreCase("")
        }
    }

    private boolean isFieldEmpty(List<Integer> idCards) {
        return idCards == null
    }

    private void validateLimitField(Integer limit) {
        if (isLimitInvalid(limit)) {
            throw new LimitInvalidException("Tamanho do limit: ${limit}. Valor maior que o limit DEFAULT: " +
                    "${DEFAULT_LIMIT}")
        }
    }

    private boolean isLimitInvalid(Integer limit) {
        return limit > DEFAULT_LIMIT
    }
}
