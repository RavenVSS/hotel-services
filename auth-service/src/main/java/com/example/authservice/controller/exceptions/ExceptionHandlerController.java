package com.example.authservice.controller.exceptions;

import com.example.authservice.controller.exceptions.dto.ErrorDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorDto handleConflict() {
        return new ErrorDto("Error query");
    }
}

