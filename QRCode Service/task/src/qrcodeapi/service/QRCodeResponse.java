package qrcodeapi.service;

import java.awt.image.BufferedImage;

import org.springframework.http.MediaType;

public record QRCodeResponse(MediaType mediaType, BufferedImage image) {
}
