package core.util;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Ranked {
    private HashMap<String, Double>[] topWords;

    public Ranked(int size) {
        topWords = (HashMap<String, Double>[]) new HashMap<?, ?>[size];
    }

    public void createMap(int i) {
        topWords[i] = new HashMap<>();
    }

    public void setTopWord(int i, String s, double d) {
        topWords[i].put(s, d);
    }

    public Stream<Map.Entry<String, Double>> getTopWord(int i) {
        return topWords[i].entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed());
    }
}
