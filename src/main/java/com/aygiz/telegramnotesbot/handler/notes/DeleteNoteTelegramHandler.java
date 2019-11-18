package com.aygiz.telegramnotesbot.handler.notes;

import com.aygiz.telegramnotesbot.handler.TelegramMessageHandler;
import com.aygiz.telegramnotesbot.model.telegram.TelegramNote;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;
import com.aygiz.telegramnotesbot.repository.telegram.TelegramNoteRepository;
import com.aygiz.telegramnotesbot.service.TelegramNotesBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeleteNoteTelegramHandler implements TelegramMessageHandler {

    ListNoteTelegramHandler listNoteTelegramHandler;
    TelegramNotesBot telegramNotesBot;
    TelegramNoteRepository telegramNoteRepository;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (!telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.DELETE_NOTE_COMMAND)
                || Objects.nonNull(telegramUpdate.getMessage().getFrom().getPerson())
                || telegramNotesBot.isNeedInputDeleteNote()) {
            return;
        }

        boolean hasNotes = listNoteTelegramHandler.showList(telegramUpdate);
        if (!hasNotes) {
            return;
        }

        Long chatId = telegramUpdate.getMessage().getChat().getId();

        telegramNotesBot.sendTextMessage(chatId, "Введите номер заметки, которую хотите удалить");
        telegramNotesBot.isNeedInputDeleteNote = true;


    }
}
