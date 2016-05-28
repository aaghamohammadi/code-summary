package core.score;

import model.MethodModel;
import model.ProgramModel;

import java.util.HashMap;
import java.util.TreeSet;

public class TFIDF {
    int numOfWords;
    double[] idfVector;
    double[][] tfIdfMatrix;
    double[][] tfMatrix;
    String[] wordVector;
    int docLength[];

    public TFIDF(String[] docs) {
        // STEP 1, scan all words and count the number of different words
        // mapWordToIdx maps word to its vector index
        HashMap<String, Integer> mapWordToIdx = new HashMap<String, Integer>();
        int nextIdx = 0;
        for (String doc : docs) {
            for (String word : doc.split(" ")) {
                if (!mapWordToIdx.containsKey(word)) {
                    mapWordToIdx.put(word, nextIdx);
                    nextIdx++;
                }
            }
        }

        numOfWords = mapWordToIdx.size();

        // STEP 2, create word vector where wordVector[i] is the actual word
        wordVector = new String[numOfWords];
        for (String word : mapWordToIdx.keySet()) {
            int wordIdx = mapWordToIdx.get(word);
            wordVector[wordIdx] = word;
        }

        // STEP 3, create doc word vector where docCountVector[i] is number of
        // docs containing word of index i
        // and doc length vector
        int[] docCountVector = new int[numOfWords];
        docLength = new int[docs.length];
        // lastDocWordVector is auxilary vector keeping track of last doc index
        // containing the word
        int[] lastDocWordVector = new int[numOfWords];
        for (int wordIdx = 0; wordIdx < numOfWords; wordIdx++) {
            lastDocWordVector[wordIdx] = -1;
        }
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            String doc = docs[docIdx];
            String[] words = doc.split(" ");
            for (String word : words) {
                docLength[docIdx] = words.length;
                int wordIdx = mapWordToIdx.get(word);
                if (lastDocWordVector[wordIdx] < docIdx) {
                    lastDocWordVector[wordIdx] = docIdx;
                    docCountVector[wordIdx]++;
                }
            }
        }

        // STEP 4, compute IDF vector based on docCountVector
        idfVector = new double[numOfWords];
        for (int wordIdx = 0; wordIdx < numOfWords; wordIdx++) {
            idfVector[wordIdx] = Math.log10(1 + (double) docs.length / (docCountVector[wordIdx]));
        }

        // STEP 5, compute term frequency matrix, tfMatrix[docIdx][wordIdx]
        tfMatrix = new double[docs.length][];
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            tfMatrix[docIdx] = new double[numOfWords];
        }
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            String doc = docs[docIdx];
            for (String word : doc.split(" ")) {
                int wordIdx = mapWordToIdx.get(word);
                tfMatrix[docIdx][wordIdx] = tfMatrix[docIdx][wordIdx] + 1;
            }
        }
        // normalize idfMatrix by deviding corresponding doc length
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            for (int wordIdx = 0; wordIdx < numOfWords; wordIdx++) {
                tfMatrix[docIdx][wordIdx] = tfMatrix[docIdx][wordIdx] / docLength[docIdx];
            }
        }

        // STEP 6, compute final TF-IDF matrix
        // tfIdfMatrix[docIdx][wordIdx] = tfMatrix[docIdx][wordIdx] * idfVector[wordIdx]
        tfIdfMatrix = new double[docs.length][];
        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            tfIdfMatrix[docIdx] = new double[numOfWords];
        }

        for (int docIdx = 0; docIdx < docs.length; docIdx++) {
            for (int wordIdx = 0; wordIdx < numOfWords; wordIdx++) {
                tfIdfMatrix[docIdx][wordIdx] = tfMatrix[docIdx][wordIdx] * idfVector[wordIdx];
            }
        }
        changeWeight(mapWordToIdx);

    }

    private void changeWeight(HashMap<String, Integer> mapWordToIdx) {
        TreeSet<String> ts = new TreeSet<String>();
        for (int i = 0; i < ProgramModel.getInstance().getMethodModel().size(); i++) {
            MethodModel m = ProgramModel.getInstance().getMethodModel().get(i);
            if (!m.getMethodName().equals("")) {
                tfIdfMatrix[i][mapWordToIdx.get(m.getMethodName())] *= 3.93;
            }
            if (!m.getReturnValue().equals("")) {
                for (String s : m.getReturnValue().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 3.93;
                }
                ts.clear();
            }
            if (!m.getParameters().equals("")) {
                for (String s : m.getParameters().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 2.69;
                }
                ts.clear();
            }
            if (!m.getIfCondition().equals("")) {
                for (String s : m.getIfCondition().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 2.08;
                }
                ts.clear();
            }
            if (!m.getSwitchCase().equals("")) {
                for (String s : m.getSwitchCase().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 2.08;
                }
                ts.clear();
            }
            if (!m.getLoopFor().equals("")) {
                for (String s : m.getLoopFor().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 1.97;
                }
                ts.clear();
            }
            if (!m.getLoopWhile().equals("")) {
                for (String s : m.getLoopWhile().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 1.97;
                }
                ts.clear();
            }
            if (!m.getLoopDo().equals("")) {
                for (String s : m.getLoopDo().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 1.97;
                }
                ts.clear();
            }
            if (!m.getMethodInvocation().equals("")) {
                for (String s : m.getMethodInvocation().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 2.94;
                }
                ts.clear();
            }
            if (!m.getAssign().equals("")) {
                for (String s : m.getAssign().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 2.44;
                }
                ts.clear();
            }
            if (!m.getLocalVariable().equals("")) {

                for (String s : m.getLocalVariable().split(" ")) {
                    ts.add(s);
                }
                for (String s : ts) {
                    tfIdfMatrix[i][mapWordToIdx.get(s)] *= 1.9;
                }
                ts.clear();
            }

        }
    }

    public double[][] getTF_IDFMatrix() {
        return tfIdfMatrix;
    }

    public String[] getWordVector() {
        return wordVector;
    }
}
