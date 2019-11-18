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
public class HelloTelegramMessageHandler implements TelegramMessageHandler{
    TelegramNotesBot telegramNotesBot;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {

        if (!telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.START_COMMAND)
                && !telegramUpdate.getMessage().getText().equals(TelegramNotesBot.HELLO_BUTTON)) {
            return;
        }

        Long chatId = telegramUpdate.getMessage().getChat().getId();
        TelegramUser user = telegramUpdate.getMessage().getFrom();
        String text = Stream.of("Привет", user.getLastName(), user.getFirstName(), ", в этом телеграм боте вы можете добавлять и удалять заметки")
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
        telegramNotesBot.sendTextMessage(chatId, text);

    }
}
