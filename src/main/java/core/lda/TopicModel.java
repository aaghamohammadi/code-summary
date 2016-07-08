package core.lda;

import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.*;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class TopicModel {
    public static void main(String[] args) throws Exception {
        ImportData importer = new ImportData();
        InstanceList instances = importer.readDirectory(new File("src/main/resources/input"));
//        instances.save(new File("src/main/resources/output"));
    }
}

class TxtFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return pathname.toString().endsWith(".txt");
    }
}

class ImportData {
    Pipe pipe;

    public ImportData() {
        this.pipe = buildPipe();
    }

    private Pipe buildPipe() {
        ArrayList pipeList = new ArrayList();
        pipeList.add(new Input2CharSequence("UTF-8"));

        Pattern tokenPattern = Pattern.compile("[\\p{L}\\p{N}_]+");
        pipeList.add(new CharSequence2TokenSequence(tokenPattern));

        pipeList.add(new TokenSequenceLowercase());
        pipeList.add(new TokenSequenceRemoveStopwords(false, false));
        pipeList.add(new TokenSequence2FeatureSequence());
        pipeList.add(new Target2Label());

        pipeList.add(new FeatureSequence2FeatureVector());
        pipeList.add(new PrintInputAndTarget());

        return new SerialPipes(pipeList);
    }

    public InstanceList readDirectory(File directory) {
        return readDirectories(new File[]{directory});
    }

    public InstanceList readDirectories(File[] directories) {

        FileIterator iterator =
                new FileIterator(directories,
                        new TxtFilter(),
                        FileIterator.LAST_DIRECTORY);


        InstanceList instances = new InstanceList(pipe);

        instances.addThruPipe(iterator);

        return instances;
    }
}
