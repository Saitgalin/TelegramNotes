package com.aygiz.telegramnotesbot.service;

import com.aygiz.telegramnotesbot.model.telegram.*;
import com.aygiz.telegramnotesbot.repository.telegram.*;
import com.aygiz.telegramnotesbot.transformer.Transformer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TelegramUpdateService {

    Transformer<Update, TelegramUpdate> updateToTelegramUpdateTransformer;
    Transformer<Message, TelegramMessage> messageToTelegramMessageTransformer;
    Transformer<User, TelegramUser> userToTelegramUserTransformer;
    Transformer<Chat, TelegramChat> chatToTelegramChatTransformer;
    Transformer<Message, TelegramNote> messageTelegramNoteTransformer;

    TelegramUpdateRepository telegramUpdateRepository;
    TelegramMessageRepository telegramMessageRepository;
    TelegramUserRepository telegramUserRepository;
    TelegramChatRepository telegramChatRepository;
    TelegramNoteRepository telegramNoteRepository;

    public TelegramUpdate save(Update update) {
        TelegramUser telegramUser = telegramUserRepository.findById(update.getMessage().getFrom().getId())
                .orElseGet(() ->
                        telegramUserRepository.save(
                                userToTelegramUserTransformer.transform(update.getMessage().getFrom())
                        )
                );

        TelegramChat telegramChat = telegramChatRepository.findById(update.getMessage().getChat().getId())
                .orElseGet(() -> {
                    TelegramChat transformedChat = chatToTelegramChatTransformer.transform((update.getMessage().getChat()));
                    transformedChat.setUser(telegramUser);
                    return telegramChatRepository.save(transformedChat);
                });

        TelegramMessage telegramMessage = messageToTelegramMessageTransformer.transform(update.getMessage());
        telegramMessage.setFrom(telegramUser);
        telegramMessage.setChat(telegramChat);
        TelegramMessage savedTelegramMessage = telegramMessageRepository.save(telegramMessage);

        /*
        TelegramNote telegramNote = messageTelegramNoteTransformer.transform(update.getMessage());
        telegramMessage.setFrom(telegramUser);
        telegramMessage.setChat(telegramChat);
        telegramNoteRepository.save(telegramNote);

         */
        TelegramUpdate telegramUpdate = updateToTelegramUpdateTransformer.transform(update);
        telegramUpdate.setMessage(savedTelegramMessage);

        return telegramUpdateRepository.save(telegramUpdate);

    }



}
