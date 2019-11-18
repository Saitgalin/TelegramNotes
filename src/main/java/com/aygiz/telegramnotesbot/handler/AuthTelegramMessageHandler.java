package com.aygiz.telegramnotesbot.handler;

import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUser;
import com.aygiz.telegramnotesbot.repository.PersonRepository;
import com.aygiz.telegramnotesbot.repository.telegram.TelegramUserRepository;
import com.aygiz.telegramnotesbot.service.TelegramNotesBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthTelegramMessageHandler implements TelegramMessageHandler {

    TelegramNotesBot telegramNotesBot;
    PersonRepository personRepository;
    TelegramUserRepository telegramUserRepository;

    @Override
    public void handle(TelegramUpdate telegramUpdate) {
        if (!telegramUpdate.getMessage().getText().startsWith(TelegramNotesBot.START_COMMAND)
        || Objects.nonNull(telegramUpdate.getMessage().getFrom().getPerson())) {
            return;
        }

        String authCode = telegramUpdate.getMessage().getText().replace(TelegramNotesBot.START_COMMAND, "").trim();
        personRepository.findByAuthCode(authCode)
                .ifPresent(person -> {
                    TelegramUser user = telegramUpdate.getMessage().getFrom();
                    user.setPerson(person);
                    telegramUserRepository.save(user);

                    Long chatId = telegramUpdate.getMessage().getChat().getId();
                    String text = "Вы были авторизованы как " + person.getName();
                    telegramNotesBot.sendTextMessage(chatId, text);
                });

    }
}
