import controller.MethodsController;
import core.scanner.LexicalAnalyzer;
import core.score.TFIDF;
import model.MethodModel;
import model.ProgramModel;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        String path = "src/main/resources/test.java";
        try {
            MethodsController methodsController = new MethodsController(path);
            String[] docs = new String[ProgramModel.getInstance().getMethodModel().size()];
            for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
                MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);
                docs[i] = LexicalAnalyzer.getInstance().getTokens(m.getMethodName() + " " + m.getMethodBody());


                System.out.println("--------------------------");

            }
            TFIDF tf_idf = new TFIDF(docs);
            for (String s : tf_idf.getWordVector()) {
                System.out.printf("%1s\t", s);
            }
            System.out.println();
            for (double[] docV : tf_idf.getTF_IDFMatrix()) {
                for (double v : docV) {
                    System.out.printf("%4f\t", v);
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

