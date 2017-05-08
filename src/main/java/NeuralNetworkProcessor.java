import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;

import java.io.File;
import java.io.IOException;
import java.rmi.ConnectIOException;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkProcessor {

    private static NeuralNetwork<org.neuroph.nnet.learning.BackPropagation> neuralNetwork;




    public static void constructNetwork(){
        // Create multiperceptron network
        neuralNetwork = new MultiLayerPerceptron(neuronsInLayers());

        // Configure backpropagation method with error and learning parameters
        MomentumBackpropagation learning = (MomentumBackpropagation) neuralNetwork.getLearningRule();
        learning.setMaxError(Configuration.maxError());
        learning.setLearningRate(Configuration.learningRate());

        neuralNetwork.setLearningRule(learning);
    }

    private static List<Integer> neuronsInLayers(){
        final int inputNeurons = Configuration.imagePixels(); // input layer
        final List<Integer> hiddenLayersNeurons = Configuration.neuronsHiddenLayers(); // n hidden layers
        final int outputNeurons = Configuration.expectedOutputs().size(); // output layer
        List<Integer> totalNeurons = new ArrayList<Integer>();
        totalNeurons.add(inputNeurons);
        totalNeurons.addAll(hiddenLayersNeurons);
        totalNeurons.add(outputNeurons);
        return totalNeurons;
    }




    public static void trainNetwork() throws IOException {
        neuralNetwork.learn(trainingSet());
    }

    private static DataSet trainingSet() throws IOException {
        final List<String> expectedOutputs = Configuration.expectedOutputs();
        final int inputVectorSize = Configuration.imagePixels();
        final int outputVectorSize = expectedOutputs.size();

        DataSet trainingSet = new DataSet(inputVectorSize,outputVectorSize); // Sets the format of training patterns

        final int imageWidth = Configuration.widthPixels();
        final int imageHeight = Configuration.heightPixels();

        for (File trainingImage : ImageLoader.trainingImagesFor(expectedOutputs)){
            final double[] inputVector = ImageLoader.load(trainingImage, imageWidth, imageHeight);
            final double[] outputVector = getExpectedOutputFor(ImageLoader.baseImageName(trainingImage));
            trainingSet.addRow(new DataSetRow(inputVector, outputVector)); // Sets the input and output data of each pattern
        }
        return trainingSet;
    }

    private static double[] getExpectedOutputFor(final String name){
        final List<String> expectedOutputs = Configuration.expectedOutputs();
        double[] data = new double[expectedOutputs.size()];
        for(int i = 0;i <expectedOutputs.size(); i++){
            data[i] = (expectedOutputs.get(i).equals(name)) ? 1 : 0;
        }
        return data;
    }




    public static void validateNetwork() throws IOException {

    }

}
