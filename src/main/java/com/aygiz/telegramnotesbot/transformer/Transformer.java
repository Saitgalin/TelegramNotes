package com.aygiz.telegramnotesbot.transformer;

public interface Transformer<FROM, TO> {
    TO transform(FROM chat);
}
