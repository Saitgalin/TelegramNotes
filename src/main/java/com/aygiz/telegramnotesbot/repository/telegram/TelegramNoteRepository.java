package com.aygiz.telegramnotesbot.repository.telegram;


import com.aygiz.telegramnotesbot.model.telegram.TelegramMessage;
import com.aygiz.telegramnotesbot.model.telegram.TelegramNote;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "telegram_notes", path = "notes")
public interface TelegramNoteRepository extends PagingAndSortingRepository<TelegramNote, Integer> {
    List<TelegramNote> findAllByFrom(TelegramUser telegramUser);

    @Override
    void deleteById(Integer integer);
}
