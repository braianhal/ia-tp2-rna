import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Configuration {

    private static Properties config = null;

    private static final String NETWORK_NAME = "network_name";
    private static final String IMAGE_WIDTH = "image_width";
    private static final String IMAGE_HEIGHT = "image_height";
    private static final String NEURONS_HIDDEN_LAYERS = "neurons_hidden_layers";
    private static final String EXPECTED_OUTPUTS = "expected_outputs";
    private static final String LEARNING_RATE = "learning_rate";
    private static final String MOMENTUM = "momentum";
    private static final String MAX_ITERATIONS = "max_iterations";
    private static final String MAX_ERROR = "max_error";
    private static final String MAX_ERROR_VALIDATION = "max_error_validation";
    private static final String TRAINING_IMAGES_DIR = "training_images_dir";
    private static final String VALIDATION_IMAGES_DIR = "validation_images_dir";
    private static final String OUTPUT_DIR = "output_dir";

    private static final String BASE_DIR = new File("").getAbsolutePath();

    public static void loadProperties() throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);
        config = prop;
    }

    public static String getNetworkName() {
        return config.getProperty(NETWORK_NAME);
    }

    public static int getWidthPixels(){
        return Integer.parseInt(config.getProperty(IMAGE_WIDTH));
    }

    public static int getHeightPixels(){
        return Integer.parseInt(config.getProperty(IMAGE_HEIGHT));
    }

    public static List<Integer> getNeuronsHiddenLayers(){
        String[] values = config.getProperty(NEURONS_HIDDEN_LAYERS).split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String value: values) {
            list.add(Integer.parseInt(value));
        }
        return list;
    }

    public static List<String> getExpectedOutputs(){
        return Arrays.asList(config.getProperty(EXPECTED_OUTPUTS).split(","));
    }

    public static double getLearningRate(){
        return Double.parseDouble(config.getProperty(LEARNING_RATE));
    }

    public static double getMomentum(){
        return Double.parseDouble(config.getProperty(MOMENTUM));
    }

    public static int getMaxIterations(){
        return Integer.parseInt(config.getProperty(MAX_ITERATIONS));
    }

    public static double getMaxError(){
        return Double.parseDouble(config.getProperty(MAX_ERROR));
    }

    public static double getMaxErrorValidation(){
        return Double.parseDouble(config.getProperty(MAX_ERROR_VALIDATION));
    }

    public static String getTrainingImagesDir(){
        return BASE_DIR + config.getProperty(TRAINING_IMAGES_DIR);
    }

    public static String getValidationImagesDir(){
        return BASE_DIR + config.getProperty(VALIDATION_IMAGES_DIR);
    }

    public static String getOutputDir(){
        return BASE_DIR + config.getProperty(OUTPUT_DIR);
    }

}
