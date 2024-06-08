package qrcodeapi.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class QRCodeService {

    public BufferedImage getQrCodeImage() throws IOException {

        final int height = 250;
        final int width = 250;

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        return bi;
    }

}
