package application;

import controller.MethodsController;
import core.generator.AutoDocGenerator;
import core.lda.TopicModel;
import core.score.Ranked;
import core.score.TFIDF;
import model.MethodModel;
import model.ProgramModel;

import java.io.*;

public class Main {

    public static void start(MainApp mainApp) throws ClassNotFoundException, InterruptedException, FileNotFoundException, UnsupportedEncodingException {
        String path = "src/main/resources/test.java";

        PrintWriter writer = new PrintWriter("src/main/resources/input/input.txt", "UTF-8");
        if (mainApp.getFiles().get(0).getName().endsWith(".java")) {
            try {
                MethodsController methodsController = new MethodsController(mainApp.getFiles().get(0).getPath(), "AvgFactor");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            find_files(mainApp.getFiles().get(0));
        }

        String[] docs = new String[ProgramModel.getInstance().getMethodModel().size()];
        for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
            MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);
            docs[i] = m.getMethodBody();
            writer.println(m.getMethodName() + " " + m.getMethodBody());
        }
        TFIDF tf_idf = new TFIDF(docs);
        Ranked ranked = new Ranked(ProgramModel.getInstance().getMethodModel().size());
        for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
            MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);
            ranked.createMap(i);
            System.out.println("-------------------------------");
            System.out.println("Method: " + m.getMethodName());
            mainApp.setMethod(m);
            System.out.println();
            for (String s : m.getDictionary().keySet()) {
//                    System.out.printf("%s:%-24s\t", m.getTypeWord().get(s), s);
//                    System.out.printf("%-12f\n", tf_idf.getTF_IDFMatrix()[i][tf_idf.getWords().get(s)]);
                ranked.setTopWord(i, s, tf_idf.getTF_IDFMatrix()[i][tf_idf.getWords().get(s)]);
            }
            // Generate Template Document
            AutoDocGenerator autoDocGenerator = new AutoDocGenerator();
            boolean[] isValid = new boolean[12];
            String[] templates = new String[12];
            ranked.getTopWord(i).limit(10).forEach((v) -> {
                System.out.println(m.getTypeWord().get(v.getKey()) + " " + v);
                switch (m.getTypeWord().get(v.getKey())) {
                    case "MethodName":
                        if (!isValid[0])
                            templates[0] = autoDocGenerator.autoDocumentMethodName(v.getKey());
                        isValid[0] = true;
                        break;
                    case "LocalVariable":
                        if (!isValid[8])
                            templates[8] = autoDocGenerator.autoDocumentLocalVriable(v.getKey());
                        isValid[8] = true;
                        break;
                    case "RetrunValue":
                        if (!isValid[11])
                            templates[11] = autoDocGenerator.autoDocumentReturnValue(m.getExpReturnValue(), v.getKey());
                        isValid[11] = true;
                        break;
                    case "Parameters":
                        if (!isValid[1])
                            templates[1] = autoDocGenerator.autoDocumentParameters(m.getExpParameters());
                        isValid[1] = true;
                        break;
                    case "LoopsFor":
                        if (!isValid[4])
                            templates[4] = autoDocGenerator.autoDocumentLoopsFor(m.getExpLoopFor(), v.getKey());
                        isValid[4] = true;
                        break;
                    case "LoopsWhile":
                        if (!isValid[5])
                            templates[5] = autoDocGenerator.autoDocumentLoopsWhile(m.getExpLoopWhile(), v.getKey());
                        isValid[5] = true;
                        break;
                    case "LoopsDo":
                        if (!isValid[6])
                            templates[6] = autoDocGenerator.autoDocumentLoopsDo(m.getExpLoopDo(), v.getKey());
                        isValid[6] = true;
                        break;
                    case "Assignment":
                        if (!isValid[9])
                            templates[9] = autoDocGenerator.autoDocumentAssignment(m.getExpAssign(), v.getKey());
                        isValid[9] = true;
                        break;
                    case "MethodInvocation":
                        if (!isValid[2])
                            templates[2] = autoDocGenerator.autoDocumentMethodInvocation(m.getExpMethodInvocation(), v.getKey());
                        isValid[2] = true;
                        break;
                    case "IF":
                        if (!isValid[7])
                            templates[7] = autoDocGenerator.autoDocumentIfCondition(m.getExpIfConditions(), v.getKey());
                        isValid[7] = true;
                        break;
                    case "SWITCH":
                        if (!isValid[3])
                            templates[3] = autoDocGenerator.autoDocumentSwitch(m.getExpSwith(), v.getKey());
                        isValid[3] = true;
                        break;
                    case "ErrorHandler":
                        if (!isValid[10])
                            templates[10] = autoDocGenerator.autoDocumentCatch(m.getExpCatch(), v.getKey());
                        isValid[10] = true;
                        break;
                }
            });
            String template = "";
            for (String s : templates) {
                if (s != null && !s.equals("")) {
                    System.out.println(s);
                    template = template + s + "\n";
                }
            }
            m.setTemplateMethod(template);
        }
        writer.close();


    }

    public static void find_files(File root) {
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                try {
                    if (file.getName().endsWith(".java")) {
                        MethodsController methodsController = new MethodsController(file.getPath(), "AvgFactor");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                find_files(file);
            }
        }
    }


}

