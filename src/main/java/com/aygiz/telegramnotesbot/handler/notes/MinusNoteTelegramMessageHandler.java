package com.aygiz.telegramnotesbot.handler.notes;

import com.aygiz.telegramnotesbot.handler.TelegramMessageHandler;
import com.aygiz.telegramnotesbot.model.telegram.TelegramNote;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;
import com.aygiz.telegramnotesbot.repository.telegram.TelegramNoteRepository;
import com.aygiz.telegramnotesbot.repository.telegram.TelegramUserRepository;
import com.aygiz.telegramnotesbot.service.TelegramNotesBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MinusNoteTelegramMessageHandler implements TelegramMessageHandler {


    TelegramNotesBot telegramNotesBot;
    TelegramNoteRepository telegramNoteRepository;
    TelegramUserRepository telegramUserRepository;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (!telegramNotesBot.isNeedInputDeleteNote()) {
            return;
        }
        if (telegramUpdate.getMessage().getText().equals("/add") || telegramUpdate.getMessage().getText().equals("/delete")) {
            return;
        }

        Long chatId = telegramUpdate.getMessage().getChat().getId();

        int noteNum = Integer.parseInt(telegramUpdate.getMessage().getText().trim());
        noteNum--;
        List<TelegramNote> telegramNotes = telegramNoteRepository.findAllByFrom(telegramUpdate.getMessage().getFrom());

        try{
            TelegramNote telegramNote = telegramNotes.get(noteNum);
            Integer telegramNoteId = telegramNote.getId();
            telegramNoteRepository.deleteById(telegramNoteId);
            telegramNotesBot.sendTextMessage(chatId, "Заметка была успешно удалена");
        } catch(Exception e) {
            telegramNotesBot.sendTextMessage(chatId, "Ошибка. Возможно вы ввели неверный номер заметки");
        }



        telegramNotesBot.isNeedInputDeleteNote = false;


    }
}
