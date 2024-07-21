package ru.nesterov.clientanalyzer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nesterov.clientanalyzer.controller.request.BadResponse;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadResponse handle(MethodArgumentNotValidException ex) {
        String message = "Ошибка по причине.....";
        BadResponse badResponse = new BadResponse();
        badResponse.setMessage(message);
        return badResponse;
    }
}
