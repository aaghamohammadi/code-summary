package core.generator;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.ForStmt;

import java.util.List;

/**
 * The type Auto doc generator.
 */
public class AutoDocGenerator {
    /**
     * Auto document method name.
     *
     * @param methodName the method name
     */
    public void autoDocumentMethodName(String methodName) {
        System.out.println("This Method " + splitCamelCase(methodName));
    }

    /**
     * Auto document local vriable.
     *
     * @param localVariable the local variable
     */
    public void autoDocumentLocalVriable(String localVariable) {
        System.out.println(localVariable + " is a local variable of this method which acts in the main process of the method");
    }

    /**
     * Auto document return value.
     *
     * @param returnValue the return value
     */
    public void autoDocumentReturnValue(String returnValue) {
        System.out.println("This method finally returns " + returnValue);
    }

    /**
     * Auto document parameters.
     *
     * @param parameters the parameters
     */
    public void autoDocumentParameters(List<Parameter> parameters) {
        System.out.print("This method gets ");
        for (Parameter p : parameters) {
            System.out.print(p.getType() + " " + p.getId().getName() + ", ");
        }
        System.out.println("as input");
    }

    public void autoDocumentLoopsFor(ForStmt loops) {
        System.out.print("some main actions of the method is done ");
        System.out.println("for " + loops.getCompare().toString().split("<")[1] + " iteration");
    }

    /**
     * Split camel case string.
     *
     * @param s the s
     * @return the string
     */
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
