package com.aygiz.telegramnotesbot.repository.telegram;

import com.aygiz.telegramnotesbot.model.telegram.TelegramUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "telegram_users", path = "users")
public interface TelegramUserRepository extends PagingAndSortingRepository<TelegramUser, Integer> {

    Optional<TelegramUser> findByUserName(String userName);

}
