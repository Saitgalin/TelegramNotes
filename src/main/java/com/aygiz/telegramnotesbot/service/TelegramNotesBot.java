package com.aygiz.telegramnotesbot.service;

import com.aygiz.telegramnotesbot.handler.TelegramMessageHandler;
import com.aygiz.telegramnotesbot.model.telegram.TelegramUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.aygiz.telegramnotesbot.transformer.Transformer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramNotesBot extends TelegramLongPollingBot {
    public static final String HELLO_BUTTON = "Привет";
    public static final String START_COMMAND = "/start";
    public static final String HELP_BUTTON = "Помощь";
    public static final String NOTES_LIST_COMMAND = "/list";
    public static final String ADD_NOTE_COMMAND = "/add";
    public static final String DELETE_NOTE_COMMAND = "/delete";

    @Getter
    @Setter
    public boolean isNeedInputNote = false;

    @Getter
    @Setter
    public boolean isNeedInputDeleteNote = false;


    @Getter
    @Value("AygizNotesBot")
    String botUsername;
    @Getter
    @Value("1046642802:AAEaw6yJ7ZWvUIvukulneVRb4PbjeaNRhpo")
    String botToken;

    final TelegramUpdateService telegramUpdateService;
    final List<TelegramMessageHandler> telegramMessageHandlers;

    @Autowired
    public TelegramNotesBot(TelegramUpdateService telegramUpdateService,
                            @Lazy List<TelegramMessageHandler> telegramMessageHandlers) {
        this.telegramMessageHandlers = telegramMessageHandlers;
        this.telegramUpdateService = telegramUpdateService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramUpdate telegramUpdate = telegramUpdateService.save(update);
        telegramMessageHandlers.forEach(
                telegramMessageHandler -> telegramMessageHandler.handle(telegramUpdate)
        );
    }

    public synchronized void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        sendMessage.setReplyMarkup(getCustomReplyKeyboardMarkup());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }



    private ReplyKeyboardMarkup getCustomReplyKeyboardMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(HELLO_BUTTON));
        keyboardFirstRow.add(new KeyboardButton(NOTES_LIST_COMMAND));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(ADD_NOTE_COMMAND));
        keyboardSecondRow.add(new KeyboardButton(DELETE_NOTE_COMMAND));



        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

}
