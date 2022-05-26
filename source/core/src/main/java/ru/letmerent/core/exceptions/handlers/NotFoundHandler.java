package ru.letmerent.core.exceptions.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.letmerent.core.exceptions.models.ApplicationError;

@ControllerAdvice
@RequiredArgsConstructor
public class NotFoundHandler {

    private final ApplicationError applicationError;

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApplicationError onUserNotFoundException(UsernameNotFoundException e) {
        return applicationError.generateError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}
