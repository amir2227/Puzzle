package ir.tehranpuzzle.mistery.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import java.util.Hashtable;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;

/**
 * Generate QR code and convert QR code to InputStream
 */
public class QrcodeGenarator {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private QrcodeGenarator() {
    }

    /**
     * Generate QR code
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static InputStream createQrcodeImage(String content) throws Exception {
        int width = 400;// QR code image width
        int height = 400;// QR code image height
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = QrcodeGenarator.toBufferedImage(bitMatrix);
        InputStream is = QrcodeGenarator.toInputStream(image);
        return is;

    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static InputStream toInputStream(BufferedImage image) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

}