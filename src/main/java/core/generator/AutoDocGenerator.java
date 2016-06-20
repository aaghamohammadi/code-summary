package core.generator;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * The type Auto doc generator.
 */
public class AutoDocGenerator {
    /**
     * Auto document method name.
     *
     * @param methodName the method name
     */
    public String autoDocumentMethodName(String methodName) {
        return "This Method " + splitCamelCase(methodName);
    }

    /**
     * Auto document local vriable.
     *
     * @param localVariable the local variable
     */
    public String autoDocumentLocalVriable(String localVariable) {
        return localVariable + " is a local variable of this method which acts in the main process of the method";
    }

    /**
     * Auto document return value.
     *
     * @param returnValue the return value
     */
    public String autoDocumentReturnValue(ArrayList<ReturnStmt> returnStmts, String returnValue) {
        TreeSet<String> t = new TreeSet<>();
        for (ReturnStmt r : returnStmts) {
            if (r.toString().contains(returnValue)) {
                t.add(r.getExpr().toString());
            }
        }
        if (t.size() > 1) {
            String temp = "This method based of different conditions return " + t;
            return temp;
        } else
            return "This method finally returns " + t;
    }

    /**
     * Auto document parameters.
     *
     * @param parameters the parameters
     */
    public String autoDocumentParameters(List<Parameter> parameters) {
        String temp = "This method gets ";
        for (Parameter p : parameters) {
            temp += p.getType() + " " + p.getId().getName() + ", ";
        }
        temp += "as input";
        return temp;
    }

    public String autoDocumentLoopsFor(ArrayList<ForStmt> loops, String loop) {
        String temp = "some main actions of the method is done ";
        for (ForStmt l : loops) {
            if (l.getInit().toString().contains(loop)) {
                temp += "for " + l.getCompare().toString().split("<")[1] + " iteration";
            }
        }
        return temp;
    }

    public String autoDocumentLoopsWhile(ArrayList<WhileStmt> loops, String loop) {
        String temp = "some main actions of the method is done ";
        for (WhileStmt l : loops) {
            if (l.getCondition().toString().contains(loop)) {
                temp += "while " + l.getCondition().toString();
            }
        }
        return temp;
    }

    public String autoDocumentLoopsDo(ArrayList<DoStmt> loops, String loop) {
        String temp = "some main actions of the method is done ";
        for (DoStmt l : loops) {
            if (l.getCondition().toString().contains(loop)) {
                temp += "while " + l.getCondition().toString();
            }
        }
        return temp;
    }

    public String autoDocumentAssignment(ArrayList<AssignExpr> assignExprs, String assign) {
        String temp = "";
        for (AssignExpr s : assignExprs) {
            if (s.getValue().toString().contains(assign)) {
                temp += " Assign equation " + s.getValue() + " to " + s.getTarget();
            }
        }
        return temp;
    }

    public String autoDocumentMethodInvocation(ArrayList<MethodCallExpr> methodCallExprs, String methodInvocation) {
        String temp = "";
        HashMap<String, Integer> t = new HashMap<>();
        for (MethodCallExpr m : methodCallExprs) {
            if (m.toString().contains(methodInvocation)) {
                if (t.containsKey(m.getName())) {
                    t.put(m.getName(), t.get(m.getName()) + 1);
                } else {
                    t.put(m.getName(), 1);
                }
            }
        }
        TreeSet<String> moreThanOne = new TreeSet<>();
        boolean hasMore = false;
        boolean hasOne = false;
        TreeSet<String> one = new TreeSet<>();
        for (String s : t.keySet()) {
            if (t.get(s) != 1) {
                hasMore = true;
                moreThanOne.add(s);
            }
            if (t.get(s) == 1) {
                one.add(s);
                hasOne = true;
            }
        }
        String output = "";
        if (hasMore == true) {
            output = "This method calls " + moreThanOne + " functions with different parameters, several times \n";
        }
        if (hasOne == true) {
            output += "This method calls " + one;
        }
        return output;
    }

    public String autoDocumentIfCondition(ArrayList<IfStmt> ifConditions, String ifCondition) {
        String temp = "";
        for (IfStmt i : ifConditions) {
            if (i.getCondition().toString().contains(ifCondition)) {
                temp += "Based on " + "(" + i.getCondition() + ")" + " if it is true, performs below steps:\n" + i.getThenStmt().toStringWithoutComments();
                if (i.getElseStmt() != null) {
                    temp += "otherwise:\n " + i.getElseStmt();
                }
            }
        }
        return temp;
    }

    public String autoDocumentSwitch(ArrayList<SwitchStmt> expSwitch, String switchCase) {
        String temp = "";
        for (SwitchStmt s : expSwitch) {
            if (s.getSelector().toString().contains(switchCase)) {
                temp += "Decides different actions based on the " + s.getSelector();
            }
        }
        return temp;
    }

    public String autoDocumentCatch(ArrayList<CatchClause> catchClauses, String catchClause) {
        String temp = "";
        for (CatchClause c : catchClauses) {
            if (c.getExcept().toString().contains(catchClause) || c.getCatchBlock().toString().contains(catchClause)) {
                temp += "This method handles error by using exception mechanisms";
            }
        }
        return temp;
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
