package ru.letmerent.core.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.letmerent.core.exceptions.models.ApplicationError;

import java.util.Date;

@ControllerAdvice
public class NotFoundHandler {

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApplicationError onUserNotFoundException(UsernameNotFoundException e) {
        return ApplicationError.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .userMessage(e.getMessage())
                .date(new Date())
                .build();
    }
}
