package dev.deadc0de.cobalt.graphics.javafx;

import java.util.function.Function;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class TwoBitColorImageFilter implements Function<Image, Image> {

    private static final int BLACK = 0x00;
    private static final int DARK_GREY = 0x40;
    private static final int GREY = 0x80;
    private static final int LIGHT_GREY = 0xC0;

    private final double blackThreshold;
    private final double darkGreyThreshold;
    private final double greyThreshold;

    public TwoBitColorImageFilter(Color blackThreshold, Color darkGreyThreshold, Color greyThreshold) {
        this.blackThreshold = blackThreshold.getBrightness();
        this.darkGreyThreshold = darkGreyThreshold.getBrightness();
        this.greyThreshold = greyThreshold.getBrightness();
    }

    @Override
    public Image apply(Image source) {
        PixelReader pixelReader = source.getPixelReader();
        WritableImage target = new WritableImage((int) source.getWidth(), (int) source.getHeight());
        PixelWriter pixelWriter = target.getPixelWriter();
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                pixelWriter.setColor(x, y, filterColor(pixelReader.getColor(x, y)));
            }
        }
        return target;
    }

    private Color filterColor(Color original) {
        final double brightness = original.grayscale().getBrightness();
        if (brightness < blackThreshold) {
            return Color.grayRgb(BLACK, original.getOpacity());
        }
        if (brightness < darkGreyThreshold) {
            return Color.grayRgb(DARK_GREY, original.getOpacity());
        }
        if (brightness < greyThreshold) {
            return Color.grayRgb(GREY, original.getOpacity());
        }
        return Color.grayRgb(LIGHT_GREY, original.getOpacity());
    }
}
