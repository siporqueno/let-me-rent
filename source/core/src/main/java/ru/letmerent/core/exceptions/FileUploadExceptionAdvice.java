package ru.letmerent.core.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.letmerent.core.exceptions.models.ApplicationError;

@ControllerAdvice
@RequiredArgsConstructor
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    private final ApplicationError applicationError;
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ApplicationError handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return applicationError.generateError(HttpStatus.BAD_REQUEST.value(), "File too large!");
    }
}