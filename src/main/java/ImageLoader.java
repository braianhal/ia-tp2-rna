import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    private static final String PATH = new File("").getAbsolutePath() + "\\input\\";
    private static final String FILE_TYPE = ".bmp";

    public static double[] load(final String name, final int width, final int height) throws IOException {
        System.out.println("Loading image " + name);
        double[] data = new double[width*height];
        BufferedImage img = ImageIO.read(new File(PATH + name + FILE_TYPE));
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                int tone = img.getRGB(i,j) & 0xFF;
                data[j*width+i] = tone / (double)255;
            }
        }
        return data;
    }

}
