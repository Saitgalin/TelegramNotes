package com.aygiz.telegramnotesbot.handler.notes;


import com.aygiz.telegramnotesbot.handler.TelegramMessageHandler;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;
import com.aygiz.telegramnotesbot.repository.telegram.TelegramNoteRepository;
import com.aygiz.telegramnotesbot.service.TelegramNotesBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AddNoteTelegramMessageHandler implements TelegramMessageHandler {

    TelegramNotesBot telegramNotesBot;
    TelegramNoteRepository telegramNoteRepository;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {


        if (!telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.ADD_NOTE_COMMAND)
                || Objects.nonNull(telegramUpdate.getMessage().getFrom().getPerson())
                || telegramNotesBot.isNeedInputNote()) {
            return;
        }

        String authCode = telegramUpdate.getMessage().getText().replace(TelegramNotesBot.START_COMMAND, "").trim();
        Long chatId = telegramUpdate.getMessage().getChat().getId();
        telegramNotesBot.sendTextMessage(chatId, "Введите заметку");

        telegramNotesBot.isNeedInputNote = true;
    }
}
