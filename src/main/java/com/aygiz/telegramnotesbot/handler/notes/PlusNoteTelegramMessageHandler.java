package com.aygiz.telegramnotesbot.handler.notes;

import com.aygiz.telegramnotesbot.handler.TelegramMessageHandler;
import com.aygiz.telegramnotesbot.model.telegram.TelegramNote;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;
import com.aygiz.telegramnotesbot.repository.PersonRepository;
import com.aygiz.telegramnotesbot.repository.telegram.TelegramNoteRepository;
import com.aygiz.telegramnotesbot.repository.telegram.TelegramUserRepository;
import com.aygiz.telegramnotesbot.service.TelegramNotesBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PlusNoteTelegramMessageHandler implements TelegramMessageHandler {


    TelegramNotesBot telegramNotesBot;
    TelegramNoteRepository telegramNoteRepository;
    TelegramUserRepository telegramUserRepository;
    PersonRepository personRepository;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {

        if (!telegramNotesBot.isNeedInputNote()) {
            return;
        }


        if (telegramUpdate.getMessage().getText().equals("/add") || telegramUpdate.getMessage().getText().equals("/delete")) {
            return;
        }

        telegramUserRepository.findByUserName(telegramUpdate.getMessage().getFrom().getUserName())
                .ifPresent( person -> {
                    TelegramNote note = new TelegramNote();
                    note.setCreationDate(LocalDateTime.now());
                    note.setFrom(telegramUpdate.getMessage().getFrom());
                    note.setId(telegramUpdate.getMessage().getId());
                    note.setText(telegramUpdate.getMessage().getText());
                    telegramNoteRepository.save(note);


                    Long chatId = telegramUpdate.getMessage().getChat().getId();
                    String text = "Заметка " + telegramUpdate.getMessage().getText() + " успешно добавлена";
                    telegramNotesBot.sendTextMessage(chatId, text);
                    telegramNotesBot.isNeedInputNote = false;

                });





    }
}
