package com.aygiz.telegramnotesbot.handler;

import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUser;
import com.aygiz.telegramnotesbot.service.TelegramNotesBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HelpTelegramMessageHandler implements TelegramMessageHandler {

    TelegramNotesBot telegramNotesBot;


    @Override
    public void handle(TelegramUpdate telegramUpdate) {

        if (!telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.HELP_BUTTON)) {
            return;
        }

        Long chatId = telegramUpdate.getMessage().getChat().getId();
        String text = "Мы вам скоро поможем, ожидайте..";

        telegramNotesBot.sendTextMessage(chatId, text);
    }
}
