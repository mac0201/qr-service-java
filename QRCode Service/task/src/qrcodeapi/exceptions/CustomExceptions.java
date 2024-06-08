package qrcodeapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomExceptions {

    public static class QRImageRequestException extends ResponseStatusException {
        public QRImageRequestException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }

}
