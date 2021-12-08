package com.project.cardgame.cards.service

import com.project.cardgame.cards.Card
import com.project.cardgame.cards.CardHeader
import com.project.cardgame.cards.exceptions.InvalidAuthentication
import com.project.cardgame.cards.exceptions.NotFoundCard
import com.project.cardgame.cards.exceptions.NotFoundCards
import com.project.cardgame.exceptions.LimitInvalidException
import com.project.cardgame.cards.repository.CardRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CardServiceImpl implements CardService {

    private final DEFAULT_LIMIT = 30

    @Value('$[card.auth}')
    private String adminAuthentication

    private CardRepository cardRepository

    CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository
    }

    @Override
    List<Card> getCards(Integer offset, Integer limit, String name) {
        List<Card> cards
        validateLimitField(limit)
        if (isNameFieldEmpty(name)) {
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
    void createCard(CardHeader cardHeader, Card card) {
        validateRequestAuth(cardHeader.auth)
        cardRepository.insert(card)
    }

    @Override
    void editCard(CardHeader cardHeader, Integer id, Card card) {
        validateRequestAuth(cardHeader.auth)
        cardRepository.edit(id, card)
    }

    @Override
    void deleteCard(CardHeader cardHeader, Integer id) {
        validateRequestAuth(cardHeader.auth)
        cardRepository.delete(id)
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

    private boolean isNameFieldEmpty(String name) {
        return name.equalsIgnoreCase("") || name == null
    }

    private void validateLimitField(Integer limit) {
        if (isLimitInvalid(limit)) {
            throw new LimitInvalidException("Tamanho do limit: ${limit} maior que o limit DEFAULT: ${DEFAULT_LIMIT}")
        }
    }

    private boolean isLimitInvalid(Integer limit) {
        return limit > DEFAULT_LIMIT
    }
}
