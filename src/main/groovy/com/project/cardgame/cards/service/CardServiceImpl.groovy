package com.project.cardgame.cards.service

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.dto.CardDTO
import com.project.cardgame.cards.dto.CardMapperImpl
import com.project.cardgame.exceptions.InvalidInputFieldException

import com.project.cardgame.exceptions.NotFoundException

import com.project.cardgame.exceptions.LimitInvalidException
import com.project.cardgame.cards.repository.CardRepository
import com.project.cardgame.handler.MessageResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class CardServiceImpl implements CardService {

    private final DEFAULT_LIMIT = 30

    private final CardRepository cardRepository
    private final CardMapperImpl cardMapper

    CardServiceImpl(CardRepository cardRepository, CardMapperImpl cardMapper) {
        this.cardRepository = cardRepository
        this.cardMapper = cardMapper
    }

    @Override
    List<CardDTO> getCards(Integer offset, Integer limit, String name) {
        validateLimitField(limit)
        List<Card> cards = cardRepository.getAll(offset, limit, name)
        return cards.stream()
                .map(cardMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    CardDTO getCardById(Integer id) {
        try{
            return cardMapper.convertToDTO(this.cardRepository.getById(id))
        } catch (EmptyResultDataAccessException ignored){
            throw new NotFoundException("Nao foi encontrada a carta com id: ${id}")
        }
    }

    @Override
    MessageResponse createCard(CardDTO cardDTO) {
        validateInputCardFields(cardDTO)
        Integer cardId = cardRepository.insert(cardMapper.convertToEntity(cardDTO))
        return new MessageResponse("Card ID: ${cardId} criada com sucesso!")
    }

    @Override
    MessageResponse editCard(Integer id, CardDTO cardDTO) {
        validateInputCardFields(cardDTO)
        try{
            Integer cardEditedId = cardRepository.edit(id, cardMapper.convertToEntity(cardDTO))
            return new MessageResponse("Card ID: ${cardEditedId} editada com sucesso!")
        } catch (EmptyResultDataAccessException ignored){
            throw new NotFoundException("Nao foi encontrada a carta com id: ${id}")
        }
    }

    @Override
    MessageResponse deleteCard(Integer id) {
        try{
            Integer cardDeletedId = cardRepository.delete(id)
            return new MessageResponse("Card ID: ${cardDeletedId} deletada com sucesso!")
        } catch (EmptyResultDataAccessException ignored){
            throw new NotFoundException("Nao foi encontrada a carta com id: ${id}")
        }
    }

    private void validateInputCardFields(CardDTO cardDTO) {
        if (isFieldEmpty(cardDTO.name)) {
            throw new InvalidInputFieldException("O campo do nome está vazio ou é nulo")
        }
        if (isFieldEmpty(cardDTO.typeCard)) {
            throw new InvalidInputFieldException("O campo do tipo de card está vazio ou é nulo")
        }
        if (isFieldEmpty(cardDTO.description)) {
            throw new InvalidInputFieldException("O campo de descrição do card está vazio ou é nulo")
        }
    }

    private boolean isFieldEmpty(String name) {
        if(name == null){
            return true
        } else{
            return name.equalsIgnoreCase("")
        }
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
