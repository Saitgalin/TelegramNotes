package com.aygiz.telegramnotesbot.model.telegram;

import com.aygiz.telegramnotesbot.model.Person;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUser {
    @Id
    Integer id;
    LocalDateTime creationDate;
    String userName;
    Boolean bot;
    String lastName;
    String firstName;
    String languageCode;

    @ManyToOne
    Person person;
    @OneToMany
    Set<TelegramNote> notes;



}
