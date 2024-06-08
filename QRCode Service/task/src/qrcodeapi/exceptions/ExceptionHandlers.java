package qrcodeapi.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import static qrcodeapi.exceptions.CustomExceptions.*;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler({ImageSizeOutOfBoundsException.class, ImageTypeNotSupportedException.class})
    public ResponseEntity<ErrorResponse> handle(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getReason()));
    }

    private record ErrorResponse(String error) { }
}
