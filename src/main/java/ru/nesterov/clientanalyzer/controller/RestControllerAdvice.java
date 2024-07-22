package ru.nesterov.clientanalyzer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nesterov.clientanalyzer.controller.request.BadResponse;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadResponse handle(MethodArgumentNotValidException ex) {
        String message = getMessage(ex.toString());
        BadResponse badResponse = new BadResponse();
        badResponse.setMessage(message);
        return badResponse;
    }

    private String getMessage(String ex) {
        String message = ex.substring(ex.indexOf("default message"));
        message = message.substring(message.indexOf("[") + 1, message.indexOf("]]")) + " " + message.substring(message.lastIndexOf("[") + 1, message.lastIndexOf("]]"));
        return message;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadResponse handle(HttpMessageNotReadableException ex) {
        String message = "Ошибка по причине: неправильный формат даты";
        BadResponse badResponse = new BadResponse();
        badResponse.setMessage(message);
        return badResponse;
    }
}
