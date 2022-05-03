package ru.letmerent.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.letmerent.core.exceptions.models.ApplicationError;

import java.util.Date;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ApplicationError handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ApplicationError.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .userMessage("File too large!")
            .date(new Date())
            .build();
    }
}