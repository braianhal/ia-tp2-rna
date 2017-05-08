import org.neuroph.core.NeuralNetwork;

import java.io.IOException;

public class Main {

    private static long startTime;
    private static long endTime;

    public static void main(String [ ] args) {
        try {
            Configuration.loadProperties();

            System.out.println("Constructing network");
            startTime = System.currentTimeMillis();
            NeuralNetworkProcessor.constructNetwork();
            endTime = System.currentTimeMillis();
            System.out.println("Network constructed in " + (endTime - startTime)/1000 + " seconds");

            System.out.println("Training network");
            startTime = System.currentTimeMillis();
            NeuralNetworkProcessor.trainNetwork();
            endTime = System.currentTimeMillis();
            System.out.println("Network trained in " + (endTime - startTime)/1000 + " seconds");

            System.out.println("Validating network");
            startTime = System.currentTimeMillis();
            NeuralNetworkProcessor.validateNetwork();
            endTime = System.currentTimeMillis();
            System.out.println("Network validated in " + (endTime - startTime)/1000 + " seconds");
        } catch (IOException e) {
            System.out.println("Some images or config files couldn't be loaded");
            e.printStackTrace();
        }
    }

}
