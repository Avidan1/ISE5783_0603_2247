package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * testing the image writer class
 *
 * @author Ziv and Avidan
 */
class ImageWriterTests {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        final int width = 800;
        final int height = 500;
        final Color blue = new Color(java.awt.Color.BLUE);
        final Color green = new Color(java.awt.Color.GREEN);

        // create file for the image
        ImageWriter imageWriter = new ImageWriter("test", width, height);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                imageWriter.writePixel(i, j, i % 50 == 0 || j % 50 == 0 ? blue : green);

        imageWriter.writeToImage();
    }
}