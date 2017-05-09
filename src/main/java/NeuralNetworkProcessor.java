import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;

import java.io.*;
import java.rmi.ConnectIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuralNetworkProcessor {

    private static NeuralNetwork<org.neuroph.nnet.learning.BackPropagation> neuralNetwork;

    private static EvaluationData evaluationData = new EvaluationData();



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
        neuralNetwork.learn(getTrainingDataset());
        evaluationData.setTotalTrainingError(neuralNetwork.getLearningRule().getTotalNetworkError());
    }

    private static DataSet getTrainingDataset() throws IOException {
        return getDataSetFor(Configuration.trainingImagesDir());
    }

    private static DataSet getValidationDataset() throws IOException {
        return getDataSetFor(Configuration.validationImagesDir());
    }


    private static DataSet getDataSetFor(final String imagesDirectory) throws IOException {
        final List<String> expectedOutputs = Configuration.expectedOutputs();
        final int inputVectorSize = Configuration.imagePixels();
        final int outputVectorSize = expectedOutputs.size();

        DataSet trainingSet = new DataSet(inputVectorSize,outputVectorSize); // Sets the format of training patterns

        final int imageWidth = Configuration.widthPixels();
        final int imageHeight = Configuration.heightPixels();

        for (File image : ImageLoader.getImagesFor(imagesDirectory, expectedOutputs)){
            final double[] inputVector = ImageLoader.load(image, imageWidth, imageHeight);
            final double[] outputVector = getExpectedOutputFor(ImageLoader.baseImageName(image));
            DataSetRow pattern = new DataSetRow(inputVector, outputVector);
            pattern.setLabel(image.getName());
            trainingSet.addRow(pattern); // Sets the input and output data of each pattern
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
        final DataSet validationSet = getValidationDataset();
        for (DataSetRow validationPattern : validationSet.getRows()) {
            neuralNetwork.setInput(validationPattern.getInput());
            neuralNetwork.calculate();
            evaluationData.addValidationOutputData(
                    validationPattern.getLabel(), validationPattern.getDesiredOutput(),
                    neuralNetwork.getOutput());
        }
    }




    public static void generateOutputData() throws FileNotFoundException, UnsupportedEncodingException {
        // Excel
        PrintWriter writer = new PrintWriter(new File(new File("").getAbsolutePath() + Configuration.outputDir() + "validation.csv"));
        writer.println("Imagen;Output generado;;Output esperado;;Error cuadrático,Válido");
        for(ValidationOutput data : evaluationData.validationOutput) {
            writer.println(
                            data.imageName + ";" +
                            data.getObtainedString() + ";" +
                            data.obtainedName + ";" +
                            data.getExpectedString() + ";" +
                            data.expectedName + ";" +
                            Double.toString(data.cuadraticError) + ";" +
                            ((data.cuadraticError <= Configuration.maxErrorValidation()) ? "Sí" : "No"));
        }
        writer.flush();
        writer.close();

        // txt
        writer = new PrintWriter(new File(new File("").getAbsolutePath() + Configuration.outputDir() + "data.txt"));
        writer.println("Error total entrenamiento: " + Double.toString(evaluationData.totalTrainingError));
        writer.println("Error cuadrático medio validación: " + Double.toString(evaluationData.meanCuadraticError()));
        writer.println("Porcentaje de outputs válidos: " + Double.toString(evaluationData.porcentajeValidOutputs()));
        writer.flush();
        writer.close();
    }

}
