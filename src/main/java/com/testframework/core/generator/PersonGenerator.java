package com.testframework.core.generator;

import com.arakelian.faker.model.Person;
import com.arakelian.faker.service.RandomPerson;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class PersonGenerator {
    public static Person createPerson() {
        return RandomPerson.get().next();
    }

    public static String createEmail() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
