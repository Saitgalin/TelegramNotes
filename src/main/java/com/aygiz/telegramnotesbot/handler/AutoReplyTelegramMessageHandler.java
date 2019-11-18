package com.aygiz.telegramnotesbot.handler;

import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUser;
import com.aygiz.telegramnotesbot.service.TelegramNotesBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AutoReplyTelegramMessageHandler implements TelegramMessageHandler {
    TelegramNotesBot telegramNotesBot;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.START_COMMAND)
            || telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.HELP_BUTTON)
            || telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.HELLO_BUTTON)
            || telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.NOTES_LIST_COMMAND)
            || telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.ADD_NOTE_COMMAND)
            || telegramNotesBot.isNeedInputNote()
            || telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.DELETE_NOTE_COMMAND)
            || telegramNotesBot.isNeedInputDeleteNote()) {
            return;
        }

        Long chatId = telegramUpdate.getMessage().getChat().getId();
        TelegramUser user = telegramUpdate.getMessage().getFrom();
        String text = Stream.of(user.getLastName(), user.getFirstName(), "сказал: ", telegramUpdate.getMessage().getText())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
        telegramNotesBot.sendTextMessage(chatId, text);

    }
}
