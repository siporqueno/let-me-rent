package ru.letmerent.core.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.letmerent.core.exceptions.models.ApplicationError;
import ru.letmerent.core.exceptions.models.Violation;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationHandler {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError onConstraintValidationException(ConstraintViolationException e) {
        List<Violation> violations = e.getConstraintViolations().stream()
                .map(v -> Violation.builder()
                        .fieldName(v.getPropertyPath().toString())
                        .message(v.getMessage())
                        .build())
                .collect(Collectors.toList());
        return ApplicationError.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .userMessage(violations)
                .date(new Date())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApplicationError onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> Violation.builder()
                        .fieldName(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        return ApplicationError.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .userMessage(violations)
                .date(new Date())
                .build();
    }
}
