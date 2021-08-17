package com.example.userservice.controller.exceptions;

import com.example.userservice.controller.exceptions.dto.ErrorDto;
import com.example.userservice.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class, LoginIsNotFreeException.class})
    @ResponseBody
    public ErrorDto handleConflict(Exception ex) {
        return new ErrorDto(ex.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorDto handleConflict() {
        return new ErrorDto("Error query");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MailSendException.class)
    @ResponseBody
    public ErrorDto handleConflictMail() {
        return new ErrorDto("Invalid mail");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(NoAccessException.class)
    @ResponseBody
    public ErrorDto handleConflictAccess(Exception ex) {
        return new ErrorDto(ex.getLocalizedMessage());
    }
}

