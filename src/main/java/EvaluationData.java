import java.util.ArrayList;
import java.util.List;

public class EvaluationData {

    public double totalTrainingError;

    public List<ValidationOutput> validationOutput = new ArrayList<ValidationOutput>();

    public void setTotalTrainingError(final double totalTrainingError){
        this.totalTrainingError = totalTrainingError;
    }

    public void addValidationOutputData(final String imageName, final double[] expected, final double[] obtained){
        validationOutput.add(new ValidationOutput(imageName, expected, obtained));
    }

    public double meanCuadraticError(){
        double total = 0;
        for(ValidationOutput data : validationOutput){
            total += data.cuadraticError;
        }
        return total / validationOutput.size();
    }

    public double porcentajeValidOutputs(){
        int valid = 0;
        for(ValidationOutput data : validationOutput){
            if(data.cuadraticError <= Configuration.maxErrorValidation()){
                valid += 1;
            }
        }
        return (valid / (double)validationOutput.size()) * 100;
    }

}
