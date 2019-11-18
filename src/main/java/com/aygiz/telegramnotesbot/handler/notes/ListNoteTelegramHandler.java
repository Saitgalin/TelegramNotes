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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListNoteTelegramHandler implements TelegramMessageHandler {

    TelegramNotesBot telegramNotesBot;
    TelegramNoteRepository telegramNoteRepository;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (!telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.NOTES_LIST_COMMAND)
                || Objects.nonNull(telegramUpdate.getMessage().getFrom().getPerson())) {
            return;
        }

        showList(telegramUpdate);



    }

    public boolean showList(TelegramUpdate telegramUpdate) {
        List<TelegramNote> telegramNotes = telegramNoteRepository.findAllByFrom(telegramUpdate.getMessage().getFrom());

        Long chatId = telegramUpdate.getMessage().getChat().getId();

        if (telegramNotes.isEmpty()) {
            telegramNotesBot.sendTextMessage(chatId, "Ваш текущий список заметок пуст");
            return false;
        }

            telegramNotesBot.sendTextMessage(chatId, "Ваш текущий список заметок: ");
            int iteration = 0;
            for (TelegramNote telegramNote : telegramNotes) {
                iteration ++;
                String text = iteration + ". " + telegramNote.getText();
                telegramNotesBot.sendTextMessage(chatId, text);
            }

            return true;



    }
}
