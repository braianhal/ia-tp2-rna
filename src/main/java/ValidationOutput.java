import java.util.List;

public class ValidationOutput {

    public String imageName;

    public double[] expected;

    public double[] obtained;

    public String expectedName;

    public String obtainedName;

    public double cuadraticError;


    public ValidationOutput(final String imageName, final double[] expected, final double[] obtained){
        this.imageName = imageName;
        this.expected = expected;
        this.obtained = obtained;
        this.expectedName = getOutputName(expected);
        this.obtainedName = validatedOutputNameFor(obtained);
        this.cuadraticError = cuadraticError();
    }

    private static String getOutputName(final double[] expectedOutput){
        final List<String> expectedOutputs = Configuration.expectedOutputs();
        for(int i = 0;i <expectedOutput.length; i++){
            if(expectedOutput[i] == 1){
                return expectedOutputs.get(i);
            }
        }
        return ""; // trucho
    }

    private static String validatedOutputNameFor(final double[] actualOutput){
        double max = 0;
        int maxI = 0;
        for(int i = 0; i < actualOutput.length; i++){
            if(actualOutput[i] > max){
                max = actualOutput[i];
                maxI = i;
            }
        }
        return Configuration.expectedOutputs().get(maxI);
    }

    public String getExpectedString(){
        return getStringFor(expected);
    }

    public String getObtainedString(){
        return getStringFor(obtained);
    }

    private String getStringFor(final double[] array){
        String row = "(";
        for(int i = 0; i < array.length; i++){
            row = row.concat(Double.toString(array[i]) + ((i == array.length - 1) ? "" : ","));
        }
        return row.concat(")");
    }

    private double cuadraticError(){
        double total = 0;
        for(int i = 0; i < expected.length; i++){
            total += Math.abs(obtained[i] - expected[i]);
        }
        return total * total;
    }

}
