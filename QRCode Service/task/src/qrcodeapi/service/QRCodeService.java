package qrcodeapi.service;

import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import qrcodeapi.exceptions.CustomExceptions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class QRCodeService {

    public QRCodeResponse getQrCodeImage(int size, String type) throws RuntimeException, IOException {
        AllowedImageType t = validateAndGetType(size, type);
        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size, size);
        return new QRCodeResponse(t.getMediaType(), bi);
    }

    private AllowedImageType validateAndGetType(int size, String type) {
        if (size < 150 || size > 350) throw new CustomExceptions.ImageSizeOutOfBoundsException();
        try {
            return AllowedImageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomExceptions.ImageTypeNotSupportedException();
        }
    }

    @Getter
    public enum AllowedImageType {
        PNG(MediaType.IMAGE_PNG),
        JPEG(MediaType.IMAGE_JPEG),
        GIF(MediaType.IMAGE_GIF);

        private final MediaType mediaType;

        AllowedImageType(MediaType mediaType) {
            this.mediaType = mediaType;
        }
    }
}
