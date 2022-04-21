package ru.letmerent.core.exceptions;

import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class ApplicationError {
    private String serviceName;
    private String errorCode;
    private String userMessage;
    private Date date;

    public ApplicationError(String serviceName, String errorCode, String userMessage) {
        this.serviceName = serviceName;
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.date = new Date();
    }
}
