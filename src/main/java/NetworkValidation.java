import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class NetworkValidation {

    private NeuralNetwork network;

    private ImageRecognitionPlugin imageRecognition;

    public void load(){
        // Carga la red neuronal del archivo .nnet en la carpeta output
        final String nnetFile = Configuration.getOutputDir() + Configuration.getNetworkName() + ".nnet";
        network = NeuralNetwork.createFromFile(nnetFile);

        // Carga el plugin de reconocimiento de imágenes
        imageRecognition = (ImageRecognitionPlugin)network.getPlugin(ImageRecognitionPlugin.class);
    }

    public void validate() throws Exception {
        final String validationDir = Configuration.getValidationImagesDir();
        final File[] images = new File(validationDir).listFiles();

        if(images == null){
            throw new Exception("No hay imánes para validar");
        }

        Arrays.asList(images).forEach(this::validate);
    }

    private void validate(final File image) {
        System.out.println("Imagen: " + image.getName());
        HashMap<String, Double> output = null;
        try {
            output = imageRecognition.recognizeImage(image);
        } catch (IOException e) {
            System.out.println("Imagen " + image.getName() + " no pudo ser validada");
        }
        System.out.println(output.toString());
    }

}
