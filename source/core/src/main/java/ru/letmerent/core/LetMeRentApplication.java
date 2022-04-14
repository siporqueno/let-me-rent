package ru.letmerent.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
@SpringBootApplication
public class LetMeRentApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(LetMeRentApplication.class, args);
        StringBuilder sb = new StringBuilder();
        ServerProperties serverProperties = application.getBean(ServerProperties.class);
        ConfigurableEnvironment env = application.getEnvironment();
        int port = serverProperties.getPort();
        sb.append("\n------------------------------------------------------------------------------\n");
        sb.append("Swagger location: ").append("http://localhost:").append(port)
                .append(env.getProperty("server.servlet.context-path"))
                .append("/swagger-ui/index.html");
        sb.append("\n------------------------------------------------------------------------------");
        log.info(sb.toString());
    }
}
