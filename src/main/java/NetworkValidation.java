import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkValidation {

    private NeuralNetwork network;

    private ImageRecognitionPlugin imageRecognition;

    public List<ValidationOutput> validationOutputs = new ArrayList<>();

    public void load(){
        // Carga la red neuronal del archivo .nnet en la carpeta output
        final String nnetFile = Configuration.getOutputDir() + Configuration.getNetworkName() + ".nnet";
        network = NeuralNetwork.createFromFile(nnetFile);

        // Carga el plugin de reconocimiento de imágenes
        imageRecognition = (ImageRecognitionPlugin)network.getPlugin(ImageRecognitionPlugin.class);
    }

    public void validate() throws Exception {
        // Carga el directorio de validación
        final String validationDir = Configuration.getValidationImagesDir();
        final File[] images = new File(validationDir).listFiles();

        if(images == null){
            throw new Exception("No hay imánes para validar");
        }

        // Valida una por una las imágenes de validación
        Arrays.asList(images).forEach(this::validate);
    }

    private void validate(final File image) {
        try {
            // Guarda los datos de validación para generar el csv de salida
            ValidationOutput validationOutput = new ValidationOutput(image.getName(), imageRecognition.recognizeImage(image));
            validationOutputs.add(validationOutput);
        } catch (IOException e) {
            System.out.println("Imagen " + image.getName() + " no pudo ser validada");
        }
    }

    public void generateOutput() throws FileNotFoundException {
        DataOutput.outputValidationData(validationOutputs);
    }

}
