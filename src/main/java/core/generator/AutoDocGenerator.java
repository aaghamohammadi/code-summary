package core.generator;

public class AutoDocGenerator {
    public void autoDocumentMethodName(String methodName) {
        System.out.println("This Method " + splitCamelCase(methodName));
    }

    public String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
