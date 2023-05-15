package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
/**
testing the image writer class
 @ author ziv and avidan
 */
class ImageWriterTests {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("test", 800, 500); //create file to the image
        for (int i=0; i<800; i++) {
            for (int j = 0; j < 500; j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(i, j, new Color(0, 0, 255));
                else
                    imageWriter.writePixel(i, j, new Color(0, 255, 0));
            }
        }
            imageWriter.writeToImage();
    }

    @Test
    void testWritePixel() {
    }
}