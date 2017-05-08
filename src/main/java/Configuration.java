import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Configuration {

    private static Properties config = null;

    private static final String IMAGE_WIDTH = "image_width";
    private static final String IMAGE_HEIGHT = "image_height";
    private static final String NEURONS_HIDDEN_LAYERS = "neurons_hidden_layers";
    private static final String EXPECTED_OUTPUTS = "expected_outputs";
    private static final String LEARNING_RATE = "learning_rate";
    private static final String MAX_ERROR = "max_error";
    private static final String MAX_ERROR_VALIDATION = "max_error_validation";
    private static final String TRAINING_IMAGES_DIR = "training_images_dir";
    private static final String VALIDATION_IMAGES_DIR = "validation_images_dir";
    private static final String OUTPUT_DIR = "output_dir";

    public static void loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);
        config = prop;
    }

    public static int imagePixels(){
        return  widthPixels() * heightPixels();
    }

    public static int widthPixels(){
        return Integer.parseInt(config.getProperty(IMAGE_WIDTH));
    }

    public static int heightPixels(){
        return Integer.parseInt(config.getProperty(IMAGE_HEIGHT));
    }

    public static List<Integer> neuronsHiddenLayers(){
        String[] values = config.getProperty(NEURONS_HIDDEN_LAYERS).split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String value: values) {
            list.add(Integer.parseInt(value));
        }
        return list;
    }

    public static List<String> expectedOutputs(){
        return Arrays.asList(config.getProperty(EXPECTED_OUTPUTS).split(","));
    }

    public static double learningRate(){
        return Double.parseDouble(config.getProperty(LEARNING_RATE));
    }

    public static double maxError(){
        return Double.parseDouble(config.getProperty(MAX_ERROR));
    }

    public static double maxErrorValidation(){
        return Double.parseDouble(config.getProperty(MAX_ERROR_VALIDATION));
    }

    public static String trainingImagesDir(){
        return config.getProperty(TRAINING_IMAGES_DIR);
    }

    public static String validationImagesDir(){
        return config.getProperty(VALIDATION_IMAGES_DIR);
    }

    public static String outputDir(){
        return config.getProperty(OUTPUT_DIR);
    }

}
