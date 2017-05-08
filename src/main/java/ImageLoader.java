import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    private static final String FILE_TYPE = ".bmp";
    private static final String BASE_PATH = new File("").getAbsolutePath();

    public static double[] load(final String name, final int width, final int height) throws IOException {
        double[] data = new double[width*height];
        System.out.println(BASE_PATH + Configuration.trainingImagesDir() + name + FILE_TYPE);
        BufferedImage img = ImageIO.read(new File(BASE_PATH + Configuration.trainingImagesDir() + name + FILE_TYPE));
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                int tone = img.getRGB(i,j) & 0xFF;
                data[j*width+i] = tone / (double)255;
            }
        }
        return data;
    }

}
