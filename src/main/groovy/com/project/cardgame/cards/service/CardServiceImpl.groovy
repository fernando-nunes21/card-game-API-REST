package com.project.cardgame.cards.service

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.exceptions.InputEmptyField
import com.project.cardgame.cards.exceptions.InvalidAuthentication
import com.project.cardgame.cards.exceptions.NotFoundCard
import com.project.cardgame.cards.exceptions.NotFoundCards
import com.project.cardgame.exceptions.LimitInvalidException
import com.project.cardgame.cards.repository.CardRepository
import org.springframework.stereotype.Service

@Service
class CardServiceImpl implements CardService {

    private final DEFAULT_LIMIT = 30

    private String adminAuthentication = "bb987b6db56204c9d3348293a9c511a0"

    private CardRepository cardRepository

    CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository
    }

    @Override
    List<Card> getCards(Integer offset, Integer limit, String name) {
        List<Card> cards
        validateLimitField(limit)
        if (isFieldEmpty(name)) {
            cards = cardRepository.getAll(offset, limit)
        } else {
            cards = cardRepository.getAllByName(offset, limit, name)
        }
        validateCardsNotNull(cards)
        return cards
    }

    @Override
    Card getCardById(Integer id) {
        try{
            return cardRepository.getById(id)
        } catch (Exception ignored){
            throw new NotFoundCard("Nao foi encontrada a carta com id: ${id}")
        }
    }

    @Override
    void createCard(String auth, Card card) {
        validateRequestAuth(auth)
        validateInputCardFields(card)
        cardRepository.insert(card)
    }

    @Override
    void editCard(String auth, Integer id, Card card) {
        validateRequestAuth(auth)
        validateInputCardFields(card)
        try{
            cardRepository.getById(id)
            cardRepository.edit(id, card)
        } catch (Exception ignored){
            throw new NotFoundCard("Nao foi encontrada a carta com id: ${id}")
        }
    }

    @Override
    void deleteCard(String auth, Integer id) {
        validateRequestAuth(auth)
        try{
            cardRepository.getById(id)
            cardRepository.delete(id)
        } catch (Exception ignored){
            throw new NotFoundCard("Nao foi encontrada a carta com id: ${id}")
        }
    }

    private void validateInputCardFields(Card card){
        if(isFieldEmpty(card.name)){
            throw new InputEmptyField("O campo do nome está vazio ou é nulo")
        }
        if(isFieldEmpty(card.typeCard)){
            throw new InputEmptyField("O campo do tipo de card está vazio ou é nulo")
        }
        if(isFieldEmpty(card.description)){
            throw new InputEmptyField("O campo de descrição do card está vazio ou é nulo")
        }
    }

    private void validateCardsNotNull(List<Card> cards){
        if(cards.size() == 0){
            throw new NotFoundCards("Não foi encontrada nenhuma carta")
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
            throw new LimitInvalidException("Tamanho do limit: ${limit}. Valor maior que o limit DEFAULT: ${DEFAULT_LIMIT}")
        }
    }

    private boolean isLimitInvalid(Integer limit) {
        return limit > DEFAULT_LIMIT
    }
}
