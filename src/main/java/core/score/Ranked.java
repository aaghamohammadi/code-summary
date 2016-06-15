package core.score;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type Ranked.
 */
public class Ranked {
    private HashMap<String, Double>[] topWords;

    /**
     * Instantiates a new Ranked.
     *
     * @param size the size
     */
    public Ranked(int size) {
        topWords = (HashMap<String, Double>[]) new HashMap<?, ?>[size];
    }

    /**
     * Create map.
     *
     * @param i the
     */
    public void createMap(int i) {
        topWords[i] = new HashMap<>();
    }

    /**
     * Sets top word.
     *
     * @param i the
     * @param s the s
     * @param d the d
     */
    public void setTopWord(int i, String s, double d) {
        topWords[i].put(s, d);
    }

    /**
     * Gets top word.
     *
     * @param i the
     * @return the top word
     */
    public Stream<Map.Entry<String, Double>> getTopWord(int i) {
        return topWords[i].entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed());
    }
}
