package core.scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    private static LexicalAnalyzer instance = null;

    protected LexicalAnalyzer() {

    }

    public static LexicalAnalyzer getInstance() {
        if (instance == null)
            instance = new LexicalAnalyzer();
        return instance;
    }

    public String getTokens(String methodBody) {
        Matcher matcher;
        String tokens = "";
        StringBuilder tokenPattern = new StringBuilder();
        for (Type tokenType : Type.values())
            tokenPattern.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern expression = Pattern.compile(tokenPattern.substring(1));

        matcher = expression.matcher(methodBody);
        while (matcher.find()) {
            for (Type t : Type.values()) {

                if (matcher.group(t.name()) != null) {
                    if (matcher.group(Type.ID.name()) != null) {
                        tokens = matcher.group(t.name()) + " " + tokens;
                    }
                }
            }
        }
        return tokens;
    }
}
