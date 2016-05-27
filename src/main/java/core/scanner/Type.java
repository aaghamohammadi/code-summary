package core.scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Type {
    KEYWORDS("abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|double|else|enum|extends|final|finally|float|for|if|implements|import|instanceof|int|interface|long|native|new|package|private|protected|public|return|short|static|super|switch|synchronized|this|throws|throw|transient|try|void|volatile|while|String|true|false"),
    ID("[A-Za-z][A-Za-z0-9]*"),
    NUM("-?[0-9]+"),
    ARITHMATICOP("[*|+|-]"),
    COMMA(","),
    RELOP("==|<"),
    ASSIGNMENTOP("="),
    LOGICALOP("&&"),
    SEMICOLON(";"),
    CLOSINGP("\\)"),
    OPENINGP("\\("),
    OPENINGCB("\\{"),
    CLOSINGCB("\\}"),
    DOT("\\."),
    EOF("\\$");

    public final String pattern;

    Type(String pattern) {
        this.pattern = pattern;
    }

    public Type getTyepFormString(String s) {
        Pattern pattern;
        Matcher matcher;
        for (Type t : values()) {
            pattern = Pattern.compile(t.pattern);
            matcher = pattern.matcher(s);
            if (matcher.matches())
                return t;
        }
        throw new IllegalArgumentException();
    }
}

