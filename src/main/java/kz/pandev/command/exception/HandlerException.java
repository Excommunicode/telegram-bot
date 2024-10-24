package kz.pandev.command.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {

        log.warn("Exception: {}, MethodArgumentNotValidException error(s): \n{}", exception.getClass().getName(),
                getExceptionMessage(exception));

        ApiError errorResponse = ApiError.builder()
                .errors(convertStackTraceToStringList(exception))
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("Incorrectly made request.")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> onNotFoundException(final NotFoundException exception) {

        log.warn("Exception: {}, Not found: \n{}", exception.getClass().getName(), getExceptionMessage(exception));

        ApiError errorResponse = ApiError.builder()
                .errors(convertStackTraceToStringList(exception))
                .status(HttpStatus.NOT_FOUND.name())
                .reason("The required object was not found. ")
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private String getExceptionMessage(Throwable exception) {

        return Arrays.stream(exception.getMessage().split("&"))
                .map(message -> "- " + message.trim())
                .collect(Collectors.joining("\n"));
    }

    private List<String> convertStackTraceToStringList(Throwable exception) {

        return Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
    }
}
