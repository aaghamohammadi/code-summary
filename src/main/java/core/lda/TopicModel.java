package core.lda;

import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.*;
import model.MethodModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.regex.Pattern;

public class TopicModel {
    private static int numTopics;
    private static ParallelTopicModel model;
    private static InstanceList instances;
    private static Alphabet dataAlphabet;

    public static void lda() throws Exception {
        ArrayList<Pipe> pipeList = new ArrayList<Pipe>();

        // Pipes: lowercase, tokenize, remove stopwords, map to features
        pipeList.add(new CharSequenceLowercase());
        pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")));
        pipeList.add(new TokenSequence2FeatureSequence());

        instances = new InstanceList(new SerialPipes(pipeList));

        Reader fileReader = new InputStreamReader(new FileInputStream(new File("src/main/resources/input/input.txt")), "UTF-8");
        instances.addThruPipe(new CsvIterator(fileReader, Pattern.compile("^(\\S*)[\\s,]*(\\S*)[\\s,]*(.*)$"),
                3, 2, 1)); // data, label, name fields
        numTopics = 3;
        model = new ParallelTopicModel(numTopics, 1.0, 0.01);
        model.addInstances(instances);
        model.setNumThreads(2);
        model.setNumIterations(100);
        model.estimate();

        dataAlphabet = instances.getDataAlphabet();
        FeatureSequence tokens = (FeatureSequence) model.getData().get(0).instance.getData();
        LabelSequence topics = model.getData().get(0).topicSequence;

        Formatter out = new Formatter(new StringBuilder(), Locale.US);
        for (int position = 0; position < tokens.getLength(); position++) {
            out.format("%s-%d ", dataAlphabet.lookupObject(tokens.getIndexAtPosition(position)), topics.getIndexAtPosition(position));
        }
        System.out.println(out);

        // Estimate the topic distribution of the first instance,
        //  given the current Gibbs state.
    }

    public static String showTopic(MethodModel methodModel) {
        Formatter out;
        String output = "";
        int id = 0;
        for (int i = 0; i < instances.size(); i++) {
            if (instances.get(i).getName().equals(methodModel.getMethodName())) {
                id = i;
                break;
            }
        }
        double[] topicDistribution = model.getTopicProbabilities(id);


        // Get an array of sorted sets of word ID/count pairs
        ArrayList<TreeSet<IDSorter>> topicSortedWords = model.getSortedWords();

        // Show top 5 words in topics with proportions for the first document
        for (int topic = 0; topic < numTopics; topic++) {
            Iterator<IDSorter> iterator = topicSortedWords.get(topic).iterator();

            out = new Formatter(new StringBuilder(), Locale.US);
            out.format("%d\t%.3f\t", topic, topicDistribution[topic]);
            int rank = 0;
            while (iterator.hasNext() && rank < 5) {
                IDSorter idCountPair = iterator.next();
                out.format("%s (%.0f) ", dataAlphabet.lookupObject(idCountPair.getID()), idCountPair.getWeight());
                rank++;
            }
            output = output + out.toString() + "\n";

        }

        // Create a new instance with high probability of topic 0
        StringBuilder topicZeroText = new StringBuilder();
        Iterator<IDSorter> iterator = topicSortedWords.get(0).iterator();

        int rank = 0;
        while (iterator.hasNext() && rank < 5) {
            IDSorter idCountPair = iterator.next();
            topicZeroText.append(dataAlphabet.lookupObject(idCountPair.getID()) + " ");
            rank++;
        }

        // Create a new instance named "test instance" with empty target and source fields.
        InstanceList testing = new InstanceList(instances.getPipe());
        testing.addThruPipe(new Instance(topicZeroText.toString(), null, "test instance", null));

        TopicInferencer inferencer = model.getInferencer();
        double[] testProbabilities = inferencer.getSampledDistribution(testing.get(0), 10, 1, 5);
        System.out.println("0\t" + testProbabilities[0]);

        return output;
    }
}


