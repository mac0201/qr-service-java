package qrcodeapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomExceptions {

    public static class ImageSizeOutOfBoundsException extends ResponseStatusException {
        public ImageSizeOutOfBoundsException() {
            super(HttpStatus.BAD_REQUEST, "Image size must be between 150 and 350 pixels");
        }
    }

    public static class ImageTypeNotSupportedException extends ResponseStatusException {
        public ImageTypeNotSupportedException() {
            super(HttpStatus.BAD_REQUEST, "Only png, jpeg and gif image types are supported");
        }
    }

}
