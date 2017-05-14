import java.util.HashMap;

public class ValidationOutput {

    public String imageName;

    public HashMap<String, Double> output = new HashMap<>();

    public ValidationOutput(final String imageName, final HashMap<String, Double> output){
        this.imageName = imageName;
        this.output = output;
    }

    public String logo(){
        return imageName.replaceAll("[0-9]","").replaceAll("[.]png", "").replaceAll("[.]jpg","");
    }

    private HashMap<String, Double> expected(){
        HashMap<String, Double> expected = new HashMap<>();
        Configuration.getExpectedOutputs().forEach(
                logo -> expected.put(logo, (logo.equals(logo())) ? 1D : 0D)
        );
        return expected;
    }

    public String getExpectedString(){
        return expected().toString();
    }

    public String getOutputString(){
        return output.toString();
    }

    public double getCuadraticError(){
        double total = expected().keySet().stream().mapToDouble(
                            logo -> Math.abs(expected().get(logo) - output.get(logo))
                        ).sum();
        return total * total;
    }

    public String chosenOutput(){
        double max = 0D;
        String maxKey = "";
        for(String k : output.keySet()) {
            if (output.get(k) > max) {
                max = output.get(k);
                maxKey = k;
            }
        }
        return maxKey;
    }

}