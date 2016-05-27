import controller.MethodsController;
import core.scanner.LexicalAnalyzer;
import model.MethodModel;
import model.ProgramModel;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/test.java";
        try {
            MethodsController methodsController = new MethodsController(path);
            for (MethodModel m : ProgramModel.getInstance().getMethodModel()) {
                System.out.println(m.getMethodName());
//                System.out.println(m.getMethodBody());
                LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(m.getMethodBody());
                lexicalAnalyzer.getTokens();
                System.out.println("--------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
