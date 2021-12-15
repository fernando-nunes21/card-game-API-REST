package com.project.cardgame.cards.service

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.dto.CardDTO
import com.project.cardgame.cards.dto.CardMapperImpl
import com.project.cardgame.cards.exceptions.InputEmptyField
import com.project.cardgame.cards.exceptions.InvalidAuthentication
import com.project.cardgame.cards.exceptions.NotFoundCard

import com.project.cardgame.exceptions.LimitInvalidException
import com.project.cardgame.cards.repository.CardRepository
import com.project.cardgame.handler.MessageResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class CardServiceImpl implements CardService {

    private final DEFAULT_LIMIT = 30

    //TODO - Mudar autenticação na mao pra InterceptHttp
    //TODO - 2 tokens (Admin) - Dentro intercepter
    private String adminAuthentication = "bb987b6db56204c9d3348293a9c511a0"

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
            throw new NotFoundCard("Nao foi encontrada a carta com id: ${id}")
        }
    }

    @Override
    MessageResponse createCard(String auth, CardDTO cardDTO) {
        validateRequestAuth(auth)
        validateInputCardFields(cardDTO)
        Integer cardId = cardRepository.insert(cardMapper.convertToEntity(cardDTO))
        return new MessageResponse("Card ID: ${cardId} criada com sucesso!")
    }

    @Override
    MessageResponse editCard(String auth, Integer id, CardDTO cardDTO) {
        validateRequestAuth(auth)
        validateInputCardFields(cardDTO)
        try{
            Integer cardEditedId = cardRepository.edit(id, cardMapper.convertToEntity(cardDTO))
            return new MessageResponse("Card ID: ${cardEditedId} editada com sucesso!")
        } catch (EmptyResultDataAccessException ignored){
            throw new NotFoundCard("Nao foi encontrada a carta com id: ${id}")
        }
    }

    @Override
    MessageResponse deleteCard(String auth, Integer id) {
        validateRequestAuth(auth)
        try{
            Integer cardDeletedId = cardRepository.delete(id)
            return new MessageResponse("Card ID: ${cardDeletedId} deletada com sucesso!")
        } catch (EmptyResultDataAccessException ignored){
            throw new NotFoundCard("Nao foi encontrada a carta com id: ${id}")
        }
    }

    private void validateInputCardFields(CardDTO cardDTO) {
        if (isFieldEmpty(cardDTO.name)) {
            throw new InputEmptyField("O campo do nome está vazio ou é nulo")
        }
        if (isFieldEmpty(cardDTO.typeCard)) {
            throw new InputEmptyField("O campo do tipo de card está vazio ou é nulo")
        }
        if (isFieldEmpty(cardDTO.description)) {
            throw new InputEmptyField("O campo de descrição do card está vazio ou é nulo")
        }
    }

    private void validateRequestAuth(String auth){
        if(!adminAuthentication.equalsIgnoreCase(auth.md5())){
            throw new InvalidAuthentication("Autenticação: ${auth} é invalida")
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
