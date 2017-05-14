import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.imgrec.ColorMode;
import org.neuroph.imgrec.FractionRgbData;
import org.neuroph.imgrec.ImageRecognitionHelper;
import org.neuroph.imgrec.image.Dimension;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NetworkTraining {

    private NeuralNetwork<MomentumBackpropagation> network;

    public void build(){
        // Setea los nombres de los logos que se van a usar
        final List<String> logos = Configuration.getExpectedOutputs();

        // Crea la red
        final Dimension inputLayer = new Dimension(Configuration.getWidthPixels(), Configuration.getHeightPixels());
        final List<Integer> hiddenLayers = Configuration.getNeuronsHiddenLayers();
        NeuralNetwork nn = ImageRecognitionHelper.createNewNeuralNetwork(
                Configuration.getNetworkName(),
                inputLayer, ColorMode.COLOR_RGB,
                logos, hiddenLayers,
                TransferFunctionType.SIGMOID);

        // Setea los parámetros de la red
        MomentumBackpropagation mb = (MomentumBackpropagation) nn.getLearningRule();
        mb.setMaxIterations(Configuration.getMaxIterations());
        mb.setLearningRate(Configuration.getLearningRate());
        mb.setMaxError(Configuration.getMaxError());
        mb.setMomentum(Configuration.getMomentum());
        nn.setLearningRule(mb);

        network = nn;
    }

    public void train() throws IOException {
        // Crea el set de datos de entrenamiento
        final File imagesDir = new File(Configuration.getTrainingImagesDir());
        final Dimension imagesSize = new Dimension(Configuration.getWidthPixels(), Configuration.getHeightPixels());
        final List<String> logos = Configuration.getExpectedOutputs();
        final Map<String,FractionRgbData> map = ImageRecognitionHelper.getFractionRgbDataForDirectory(imagesDir, imagesSize); // convierte las imágenes a datos RGB (sólo considera JPG o PNG)
        final DataSet dataSet = ImageRecognitionHelper.createRGBTrainingSet(logos, map); // convierte los datos RGB a array de valores entre 0 y 1

        // Entrena a la red con el set de datos
        network.learn(dataSet);
    }

    public void save() throws FileNotFoundException {
        // Guarda la red en formato .nnet
        final String outputName = Configuration.getOutputDir() + Configuration.getNetworkName() + ".nnet";
        network.save(outputName);

        // Genera archivo de salida con datos del entrenamiento
        DataOutput.outputTrainingData(network);
    }

}
