package ru.letmerent.core.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SwaggerProperties.
 *
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SwaggerProperties {

    /**
     * Название проекта
     */
    private String title;

    /**
     * Описание
     */
    private String description;

    /**
     * Контактное лицо
     */
    private Contact contact = new Contact();

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact {

        /**
         * Имя команды
         */
        private String name;

        /**
         * Ссылка на сайт учебного заведения
         */
        private String url;

        /**
         * Электронная почта для связи с командой
         */
        private String mail;

    }
}
