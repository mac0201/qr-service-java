package qrcodeapi.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qrcodeapi.service.QRCodeResponse;
import qrcodeapi.service.QRCodeService;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api")

@AllArgsConstructor
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void health() { }

    // Retrieve QR code images
    @GetMapping("/qrcode")
    public ResponseEntity<BufferedImage> qrcode(
            @RequestParam String contents,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String correction) {
        // All validation performed by the service
        QRCodeResponse image = qrCodeService.getQrCodeImage(contents, size, type, correction);
        return ResponseEntity.ok()
                .contentType(image.mediaType())
                .body(image.image());
    }

}
