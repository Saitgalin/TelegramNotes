package com.aygiz.telegramnotesbot.transformer;

import com.aygiz.telegramnotesbot.model.telegram.TelegramChat;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.time.LocalDateTime;

@Component
public class ChatToTelegramChatTransformer implements Transformer<Chat, TelegramChat> {


    @Override
    public TelegramChat transform(Chat chat) {
        return TelegramChat.builder()
                .id(chat.getId())
                .creationDate(LocalDateTime.now())
                .userChat(chat.isUserChat())
                .groupChat(chat.isGroupChat())
                .channelChat(chat.isChannelChat())
                .superGroupChat(chat.isSuperGroupChat())
                .build();
    }
}
