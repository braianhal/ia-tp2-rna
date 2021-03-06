import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String [ ] args) {
        try {
            // Carga el archivo de configuración
            Configuration.loadProperties();

            // Realiza la tarea (entrenar o validar)
            System.out.print("Tarea a realizar: ");
            switch(new BufferedReader(new InputStreamReader(System.in)).readLine()){
                case("entrenar"):
                case("Entrenar"):
                    train();
                break;
                case("validar"):
                case("Validar"):
                    validate();
                break;
                case("crear"):
                case("Crear"):
                    create();
                    break;
                default:
                    throw new Exception("No se reconoce el comando ingresado");
            }
        } catch (IOException e) {
            System.out.println("Hubo un error cargando las propiedades, construyendo, entrenando o cargando la red");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void train() throws IOException {
        long time = System.currentTimeMillis();
        System.out.println("Construyendo red...");

        NetworkTraining networkTraining = new NetworkTraining();
        networkTraining.build();

        System.out.println("Red construida en " + ((System.currentTimeMillis()-time)/1000) +" segundos");
        time = System.currentTimeMillis();
        System.out.println("Entrenando red...");

        networkTraining.train();
        networkTraining.save();

        System.out.println("Red entrenada en " + ((System.currentTimeMillis()-time)/1000) +" segundos");
    }

    private static void validate() throws Exception {
        long time = System.currentTimeMillis();
        System.out.println("Validando red...");

        NetworkValidation networkValidation = new NetworkValidation();
        networkValidation.load();
        networkValidation.validate();
        networkValidation.generateOutput();

        System.out.println("Red validada en " + ((System.currentTimeMillis()-time)/1000) +" segundos");
    }

    private static void create() throws FileNotFoundException {
        long time = System.currentTimeMillis();
        System.out.println("Creando red...");

        NetworkTraining networkTraining = new NetworkTraining();
        networkTraining.build();
        networkTraining.save();

        System.out.println("Red creada en " + ((System.currentTimeMillis()-time)/1000) +" segundos");
    }

}
