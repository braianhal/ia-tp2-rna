import org.neuroph.core.NeuralNetwork;

import java.io.IOException;

public class Main {

    public static void main(String [ ] args) {
        try {
            NeuralNetworkProcessor.trainNetwork();
            NeuralNetworkProcessor.validateNetwork();
        } catch (IOException e) {
            System.out.println("Some input images couldn't be loaded");
            e.printStackTrace();
        }
    }

}
