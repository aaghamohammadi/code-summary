package model;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.ForStmt;

import java.util.HashMap;
import java.util.List;

public class MethodModel {
    private String methodName = "";
    private String methodBody = "";

    private String returnValue = "";
    private String expReturnValue = "";

    private String parameters = "";
    private List<Parameter> expParameters;

    private String ifCondition = "";
    private String switchCase = "";
    private String loopFor = "";
    private ForStmt expLoopFor;
    private String loopWhile = "";
    private String loopDo = "";
    private String methodInvocation = "";
    private String assign = "";
    private String localVariable = "";
    private HashMap<String, Double> dictionary = new HashMap<String, Double>();
    private HashMap<String, String> typeWord = new HashMap<>();

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setExpReturnValue(String returnValue) {
        expReturnValue = returnValue;
    }

    public void setExpParameters(List<Parameter> parameters) {
        expParameters = parameters;
    }

    public List<Parameter> getExpParameters() {
        return expParameters;
    }

    public String getExpReturnValue() {
        return expReturnValue;
    }

    public void setMethodBody(String methodBody) {
        this.methodBody = methodBody;
    }

    public void setParameter(String parameters) {
        this.parameters = parameters;
    }

    public HashMap<String, Double> getDictionary() {
        return dictionary;
    }

    public HashMap<String, String> getTypeWord() {
        return typeWord;
    }

    public void initDictionary() {
        for (String s : methodBody.split(" ")) {
            dictionary.put(s, 1.0);
            typeWord.put(s, "Type");
        }
    }

    public void setDictionary(String s, double d, String type) {
        if (dictionary.containsKey(s)) {
            if (dictionary.get(s) < d) {
                typeWord.put(s, type);
                dictionary.put(s, d);
            }

        }
    }

    public void setReturnValue(String returnValue) {
        this.returnValue += returnValue;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public String getParameters() {
        return parameters;
    }

    public String getMethodBody() {
        return methodBody;
    }

    public String getMethodName() {

        return methodName;
    }

    public void setIfCondition(String ifCondition) {
        this.ifCondition += ifCondition;
    }

    public String getIfCondition() {
        return ifCondition;
    }

    public void setLoopFor(String loopFor) {
        this.loopFor += loopFor;
    }

    public String getLoopFor() {
        return loopFor;
    }

    public void setLoopWhile(String loopWhile) {
        this.loopWhile += loopWhile;
    }

    public String getLoopWhile() {
        return loopWhile;
    }

    public void setSwitchCase(String switchCase) {
        this.switchCase += switchCase;
    }

    public String getSwitchCase() {
        return switchCase;
    }

    public void setLoopDo(String loopDo) {
        this.loopDo += loopDo;
    }

    public String getLoopDo() {
        return loopDo;
    }

    public void setMethodInvocation(String methodInvocation) {
        this.methodInvocation += methodInvocation;
    }

    public String getMethodInvocation() {
        return methodInvocation;
    }

    public void setAssign(String assign) {
        this.assign += assign;
    }

    public String getAssign() {
        return assign;
    }

    public void setLocalVariable(String localVariable) {
        this.localVariable = localVariable;
    }

    public String getLocalVariable() {
        return localVariable;
    }

    public void setExpLoopFor(ForStmt expLoopFor) {
        this.expLoopFor = expLoopFor;
    }

    public ForStmt getExpLoopFor() {
        return expLoopFor;
    }
}
