import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;

import java.io.IOException;

public class NeuralNetworkProcessor {

    private static final int IMAGE_WIDTH = 100;
    private static final int IMAGE_HEIGHT = 100;
    private static final int HIDDEN_LAYER_NEURONS = 20;
    private static final String[] LOGOS = { "facebook", "twitter", "google", "mercadolibre", "wikipedia", "instagram", "youtube"};

    private static NeuralNetwork neuralNetwork;

    public static void trainNetwork() throws IOException {
        System.out.println("Constructing network");
        neuralNetwork = new MultiLayerPerceptron(IMAGE_WIDTH*IMAGE_HEIGHT,HIDDEN_LAYER_NEURONS,7);
        System.out.println("Training network");
        neuralNetwork.learn(getTrainingSet());
    }

    private static DataSet getTrainingSet() throws IOException {
        DataSet trainingSet = new DataSet(IMAGE_WIDTH*IMAGE_HEIGHT,LOGOS.length);
        for (String logo : LOGOS) {
            trainingSet.addRow(new DataSetRow(ImageLoader.load(logo, IMAGE_WIDTH, IMAGE_HEIGHT), getExpectedOutputFor(logo)));
        }
        return trainingSet;
    }

    private static double[] getExpectedOutputFor(final String name){
        double[] data = new double[LOGOS.length];
        for(int i = 0;i <LOGOS.length; i++){
            data[i] = (LOGOS[i].equals(name)) ? 1 : 0;
        }
        return data;
    }

    public static void validateNetwork() throws IOException {
        System.out.println("Validating network");
        neuralNetwork.setInput(ImageLoader.load("facebook_validation", IMAGE_WIDTH, IMAGE_HEIGHT));
        neuralNetwork.calculate();
        double[] outputData = neuralNetwork.getOutput();
        for(int i = 0;i < outputData.length; i++){
            System.out.println(LOGOS[i] + ": " + outputData[i]);
        }
    }

}
