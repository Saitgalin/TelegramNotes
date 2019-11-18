package com.aygiz.telegramnotesbot.transformer;

import com.aygiz.telegramnotesbot.model.telegram.TelegramMessage;
import com.aygiz.telegramnotesbot.model.telegram.TelegramNote;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;

@Component
public class NoteToTelegramNoteTransformer implements Transformer<Message, TelegramNote> {
    @Override
    public TelegramNote transform(Message message) {
        return TelegramNote.builder()
                .id(message.getMessageId())
                .creationDate(LocalDateTime.now())
                .text(message.getText())
                .build();
    }
}
