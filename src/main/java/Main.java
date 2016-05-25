import controller.MethodsController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/test.java";
        try {
            MethodsController methodsController = new MethodsController(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
