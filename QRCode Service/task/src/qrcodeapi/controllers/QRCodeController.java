package qrcodeapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QRCodeController {


    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public String health() {
        return null;
    }

    // Retrieve QR code images
    @GetMapping("/qrcode")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public String qrcode() {
        return null;
    }

}
