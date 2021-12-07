package com.project.cardgame.cards

import com.project.cardgame.cards.exceptions.LimitInvalidException
import com.project.cardgame.cards.exceptions.OffsetInvalidException
import org.springframework.stereotype.Service

@Service
class CardServiceImpl implements CardService {

    private final DEFAULT_LIMIT = 30

    @Override
    List<Card> getCards(Integer offset, Integer limit, String name) {
        validatePageFields(offset, limit)
        if (isNameFieldEmpty(name)) {
            //Chamar o cara do getAll
        } else {
            //Chamar o cara do getByName
        }
        return null
    }

    @Override
    Card getCardById(Integer id) {
        return null
    }

    @Override
    void createCard(Card card) {

    }

    @Override
    void editCard(Integer id, Card card) {

    }

    @Override
    void deleteCard(Integer id) {

    }

    private boolean isNameFieldEmpty(String name) {
        return name.equalsIgnoreCase("")
    }

    private void validatePageFields(Integer offset, Integer limit) {
        if (isOffsetInvalid(offset, limit)) {
            throw new OffsetInvalidException("Tamanho do offset: ${offset} maior ou igual ao limit: ${limit}")
        }
        if (isLimitInvalid(limit)) {
            throw new LimitInvalidException("Tamanho do limit: ${limit} maior que o limit DEFAULT: ${DEFAULT_LIMIT}")
        }
    }

    private boolean isOffsetInvalid(Integer offset, Integer limit) {
        return offset >= limit
    }

    private boolean isLimitInvalid(Integer limit) {
        return limit > DEFAULT_LIMIT
    }
}
