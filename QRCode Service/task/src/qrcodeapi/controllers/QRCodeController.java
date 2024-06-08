package qrcodeapi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import qrcodeapi.service.QRCodeService;

import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/api")

@AllArgsConstructor
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public String health() {
        return null;
    }

    // Retrieve QR code images
    @GetMapping("/qrcode")
    public ResponseEntity<BufferedImage> qrcode() throws IOException {
        BufferedImage image = qrCodeService.getQrCodeImage();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }

}
