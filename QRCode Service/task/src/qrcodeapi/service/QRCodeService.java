package qrcodeapi.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import qrcodeapi.exceptions.CustomExceptions.QRImageRequestException;

import java.awt.image.BufferedImage;

@Service
public class QRCodeService {

    public QRCodeResponse getQrCodeImage(String contents, int size, String type) {
        AllowedImageType t = validateAndGetType(contents, size, type);
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            return new QRCodeResponse(t.getMediaType(), bufferedImage);

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    private AllowedImageType validateAndGetType(String contents, int size, String type) {
        if (contents.trim().isEmpty()) throw new QRImageRequestException(ErrorMessage.CONTENTS.getMessage());
        if (size < 150 || size > 350) throw new QRImageRequestException(ErrorMessage.SIZE.getMessage());
        try {
            return AllowedImageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new QRImageRequestException(ErrorMessage.TYPE.getMessage());
        }
    }

    @AllArgsConstructor
    @Getter
    private enum ErrorMessage {
        SIZE("Image size must be between 150 and 350 pixels"),
        TYPE("Only png, jpeg and gif image types are supported"),
        CONTENTS("Contents cannot be null or blank");
        private final String message;
    }

    @AllArgsConstructor
    @Getter
    public enum AllowedImageType {
        PNG(MediaType.IMAGE_PNG),
        JPEG(MediaType.IMAGE_JPEG),
        GIF(MediaType.IMAGE_GIF);
        private final MediaType mediaType;
    }
}
