import controller.MethodsController;
import core.generator.AutoDocGenerator;
import core.score.TFIDF;
import core.score.Ranked;
import model.MethodModel;
import model.ProgramModel;

import java.io.IOException;
import java.util.Map;

import static javafx.scene.input.KeyCode.K;
import static javafx.scene.input.KeyCode.V;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        String path = "src/main/resources/test.java";
        try {
            MethodsController methodsController = new MethodsController(path, "AvgFactor");
            String[] docs = new String[ProgramModel.getInstance().getMethodModel().size()];
            for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
                MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);
                docs[i] = m.getMethodBody();
            }
            TFIDF tf_idf = new TFIDF(docs);
            Ranked ranked = new Ranked(ProgramModel.getInstance().getMethodModel().size());
            for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
                MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);
                ranked.createMap(i);
                System.out.println("-------------------------------");
                System.out.println("Method: " + m.getMethodName());
                System.out.println();
                for (String s : m.getDictionary().keySet()) {
//                    System.out.printf("%s:%-24s\t", m.getTypeWord().get(s), s);
//                    System.out.printf("%-12f\n", tf_idf.getTF_IDFMatrix()[i][tf_idf.getWords().get(s)]);
                    ranked.setTopWord(i, s, tf_idf.getTF_IDFMatrix()[i][tf_idf.getWords().get(s)]);
                }
                // Generate Template Document
                AutoDocGenerator autoDocGenerator = new AutoDocGenerator();
                ranked.getTopWord(i).limit(5).forEach(System.out::println);
                ranked.getTopWord(i).limit(5).forEach((v) -> {
                    System.out.println(m.getTypeWord().get(v.getKey()));
                    switch (m.getTypeWord().get(v.getKey())) {
                        case "MethodName":
                            autoDocGenerator.autoDocumentMethodName(v.getKey());
                            break;
                        case "LocalVariable":
                            autoDocGenerator.autoDocumentLocalVriable(v.getKey());
                            break;
                        case "RetrunValue":
                            autoDocGenerator.autoDocumentReturnValue(m.getExpReturnValue());
                            break;
                        case "Parameters":
                            autoDocGenerator.autoDocumentParameters(m.getExpParameters());
                            break;
                        case "LoopsFor":
                            autoDocGenerator.autoDocumentLoopsFor(m.getExpLoopFor());

                    }
                });
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

