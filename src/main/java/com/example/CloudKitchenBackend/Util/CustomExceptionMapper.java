package com.example.CloudKitchenBackend.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
@Slf4j
@ControllerAdvice
public class CustomExceptionMapper extends ResponseEntityExceptionHandler {
//    private static final String internalServerErrorMsg = "Something went wrong. Please try again later";

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<Object> restExceptionHandler(CustomException restException) {
        log.error("Exception handling:: " + restException.getMessage());
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(restException.getType().getCode()), restException.getType().getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> restExceptionHandler(Exception ex) {
        log.error("Exception handling:: " + ex.fillInStackTrace());
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException
                                                                          methodArgumentNotValidException,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        HashMap<String, String> beanValidation = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach($ -> {
            if ($ instanceof FieldError) {
                final FieldError fe = (FieldError) $;
                beanValidation.put(fe.getField(), $.getDefaultMessage());
            }
        });
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), beanValidation);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        log.error("Exception handling BY handleHttpRequestMethodNotSupported:: " + ex.getMessage());
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleExceptionInternal:: " + ex.getMessage());
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleNoHandlerFoundException:: " + ex.getMessage());
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleBindException:: " + ex.getMessage());
        HashMap<String, String> beanValidation = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach($ -> {
            if ($ instanceof FieldError) {
                final FieldError fe = (FieldError) $;
                beanValidation.put(fe.getField(), $.getDefaultMessage());
            }
        });

        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), beanValidation);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleServletRequestBindingException:: " + ex.getMessage());
//        ex.printStackTrace();
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleMissingServletRequestPart:: " + ex.getMessage());
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleMissingServletRequestParameter:: " + ex.getMessage());
//        ex.printStackTrace();
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleHttpMediaTypeNotSupported:: " + ex.getMessage());
//        ex.printStackTrace();
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleHttpMediaTypeNotAcceptable:: " + ex.getMessage());
//        ex.printStackTrace();
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.NOT_ACCEPTABLE),
                HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleHttpMediaTypeNotAcceptable:: " + ex.getMessage());
//        ex.printStackTrace();
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleHttpMediaTypeNotAcceptable:: " + ex.getMessage());
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleTypeMismatch:: " + ex.getMessage());
        ex.printStackTrace();
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleHttpMessageNotReadable:: " + ex.getMessage());
//        ex.printStackTrace();
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception handling BY handleHttpMessageNotWritable:: " + ex.getMessage());
//        ex.printStackTrace();
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        log.error("Exception handling BY handleAsyncRequestTimeoutException:: " + ex.getMessage());
        RestResponse.error errorResponse = new RestResponse.error(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE),
                HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
