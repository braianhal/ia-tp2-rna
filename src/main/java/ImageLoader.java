import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {

    private static final String BASE_PATH = new File("").getAbsolutePath();

    public static double[] load(final File image, final int width, final int height) throws IOException {
        System.out.println("Loading: " + image.getName());
        double[] data = new double[width*height];
        BufferedImage img = ImageIO.read(image);
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                int color = img.getRGB(i,j);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color & 0xFF);
                int tone = (r+g+b) / 3;
                data[j*width+i] = tone / (double)255;
            }
        }
        return data;
    }

    public static List<File> getImagesFor(final String directory, final List<String> expectedOutputs) {
        File folder =  new File(BASE_PATH + directory);
        List<File> trainingImages = new ArrayList<File>();
        for (final File fileEntry : folder.listFiles()) {
            if(expectedOutputs.contains(baseImageName(fileEntry))){
                trainingImages.add(fileEntry);
            }
        }
        return trainingImages;
    }

    public static String baseImageName(final File image){
        return image.getName().split("_")[0];
    }

}
