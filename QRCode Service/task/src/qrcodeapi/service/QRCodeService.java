package qrcodeapi.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import qrcodeapi.exceptions.CustomExceptions.QRImageRequestException;

import java.awt.image.BufferedImage;
import java.util.Map;

@Service
@AllArgsConstructor
public class QRCodeService {

    private final QRCodeWriter writer;

    public QRCodeResponse getQrCodeImage(String contents, Integer size, String type, String correctionLevel) {
        ImageProperties properties = validateAndBuildProperties(contents, size, type, correctionLevel);
        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, properties.size, properties.size, properties.correctionMap);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            return new QRCodeResponse(properties.imageType.getMediaType(), bufferedImage);

        } catch (WriterException e) {
            throw new QRImageRequestException("Failed to generate QR code: " + e);
        }
    }

    private ImageProperties validateAndBuildProperties(String contents, Integer size, String type, String correctionLevel) {
        var builder = ImageProperties.builder();

        // validate contents
        if (contents.trim().isEmpty()) throw new QRImageRequestException(ErrorMessage.CONTENTS.getMessage());

        // validate size
        if (size != null) {
            if (size < 150 || size > 350) throw new QRImageRequestException(ErrorMessage.SIZE.getMessage());
            else builder.size(size);
        }

        // validate correction level
        try {
            // build map with EncodeHintType key and ErrorCorrectionLevel value
            if (correctionLevel != null) builder.correctionMap(
                    Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.valueOf(correctionLevel.toUpperCase())));
        } catch (IllegalArgumentException e) {
            throw new QRImageRequestException(ErrorMessage.ERROR_CORRECTION.getMessage());
        }

        // validate type
        try {
            if (type != null) builder.imageType(AllowedImageType.valueOf(type.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new QRImageRequestException(ErrorMessage.TYPE.getMessage());
        }

        builder.contents(contents);
        return builder.build();
    }

    @AllArgsConstructor
    @Builder
    private static class ImageProperties {
        private String contents;

        @Builder.Default
        private int size = 250;

        @Builder.Default
        private Map<EncodeHintType, ErrorCorrectionLevel> correctionMap = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        @Builder.Default
        private AllowedImageType imageType = AllowedImageType.PNG;
    }

    @AllArgsConstructor
    @Getter
    private enum ErrorMessage {
        SIZE("Image size must be between 150 and 350 pixels"),
        TYPE("Only png, jpeg and gif image types are supported"),
        CONTENTS("Contents cannot be null or blank"),
        ERROR_CORRECTION("Permitted error correction levels are L, M, Q, H");
        private final String message;
    }

    @AllArgsConstructor
    @Getter
    private enum AllowedImageType {
        PNG(MediaType.IMAGE_PNG),
        JPEG(MediaType.IMAGE_JPEG),
        GIF(MediaType.IMAGE_GIF);
        private final MediaType mediaType;
    }
}
