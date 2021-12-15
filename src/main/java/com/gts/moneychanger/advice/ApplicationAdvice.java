package com.gts.moneychanger.advice;

import com.gts.moneychanger.exception.BadRequestException;
import com.gts.moneychanger.exception.NotAcceptableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@ControllerAdvice
@Slf4j
public class ApplicationAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(code = BAD_REQUEST)
    String exceptionHandler(BadRequestException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(NotAcceptableException.class)
    @ResponseBody
    @ResponseStatus(code = NOT_ACCEPTABLE)
    String exceptionHandler(NotAcceptableException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }
}
