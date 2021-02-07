package com.healthfoodcoin.qr.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping
public class QRController {

    @GetMapping(value = "/qr/generate/{text}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] generateQR(@PathVariable("text") String text) throws Exception {
        return getQR(text);
    }

    @GetMapping(value = "/qr/freecoin", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] freecoin() throws Exception {
        return getQR("http://192.168.2.25:8080/freecoin/display");
    }

    @GetMapping(value = "freecoin/display")
    @ResponseBody
    public String displayfreecoin() throws Exception {
        return "You received 5 free coins";
    }

    private byte[] getQR(String barcode) throws WriterException, IOException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcode, BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }
}
