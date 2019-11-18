package com.aygiz.telegramnotesbot.handler;

import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;

public interface TelegramMessageHandler {
    void handle(TelegramUpdate telegramUpdate);

}
