import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.learning.MomentumBackpropagation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataOutput {

    public static void outputTrainingData(NeuralNetwork<MomentumBackpropagation> network) throws FileNotFoundException {
        final double trainingError = network.getLearningRule().getTotalNetworkError();
        final String outputFile = Configuration.getOutputDir() + "training.txt";

        PrintWriter writer = writer = new PrintWriter(new File(outputFile));
        writer.println("Error total entrenamiento: " + Double.toString(trainingError));
        writer.flush();
        writer.close();
    }

    public static void outputValidationData(final List<ValidationOutput> data) throws FileNotFoundException {
        // Excel
        String outputFile = Configuration.getOutputDir() + "validation.csv";
        PrintWriter writer = new PrintWriter(new File(outputFile));

        writer.println("Imagen;Output generado;Logo reconocido;Output esperado;Logo esperado;Error cuadratico;Respuesta correcta;Respuesta confiable");
        for(ValidationOutput d : data) {
            writer.println(
                            d.imageName + ";" +
                            d.getOutputString() + ";" +
                            d.chosenOutput() + ";" +
                            d.getExpectedString() + ";" +
                            d.logo() + ";" +
                            Double.toString(d.getCuadraticError()) + ";" +
                            (d.logo().equals(d.chosenOutput()) ? "Si" : "No") + ";" +
                            ((d.getCuadraticError() <= Configuration.getMaxErrorValidation()) ? "Si" : "No"));
        }
        writer.flush();
        writer.close();

        // txt
        outputFile = Configuration.getOutputDir() + "validation.txt";
        writer = writer = new PrintWriter(new File(outputFile));
        writer.println("Error cuadrático medio: " + asString(cuadraticMeanError(data)));
        writer.println("Porcentaje de respuestas correctas: " + asString(percentajeCorrect(data)));
        writer.println("Porcentaje de respuestas confiables: " + asString(percentajeReliable(data)));
        writer.flush();
        writer.close();

    }

    private static double cuadraticMeanError(final List<ValidationOutput> data){
        return data.stream().mapToDouble(ValidationOutput::getCuadraticError).sum() / data.size();
    }

    private static double percentajeCorrect(final List<ValidationOutput> data){
        return (data.stream().filter(
                d -> d.logo().equals(d.chosenOutput())
        ).collect(Collectors.toList()).size() / (double)data.size()) * 100;
    }

    private static double percentajeReliable(final List<ValidationOutput> data){
        return (data.stream().filter(
                d -> d.getCuadraticError() < Configuration.getMaxErrorValidation()
        ).collect(Collectors.toList()).size() / (double)data.size()) * 100;
    }

    private static String asString(final Double val){
        return Double.toString(Math.floor(val*100000)/100000);
    }

}
