package core.scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    private Matcher matcher;

    public LexicalAnalyzer(String methodBody) {
        StringBuilder tokenPattern = new StringBuilder();
        for (Type tokenType : Type.values())
            tokenPattern.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern expression = Pattern.compile(tokenPattern.substring(1));
        matcher = expression.matcher(methodBody);
    }

    public void getTokens() {

        while (matcher.find()) {
            for (Type t : Type.values()) {

                if (matcher.group(t.name()) != null) {
                    if (matcher.group(Type.KEYWORDS.name()) != null) {
                        System.out.println(matcher.group(t.name()));
                    }
                    if (matcher.group(Type.ID.name()) != null) {
                        System.out.println(matcher.group(t.name()));
                    }
                }
            }
        }
    }
}
