package nl.kristis.vaskys.iot.demo.iotdemorelay42.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.ConnectException;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ExceptionResponseMessage handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ExceptionResponseMessage("The provided object is invalid.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ExceptionResponseMessage handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ExceptionResponseMessage("The request is invalid.");
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ExceptionResponseMessage handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ExceptionResponseMessage("Request method used is not allowed for this endpoint.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ConnectException.class)
    private ExceptionResponseMessage handleDbConnectionException(ConnectException e) {
        return new ExceptionResponseMessage("Can not connect to database. Please contact support.");
    }
}
