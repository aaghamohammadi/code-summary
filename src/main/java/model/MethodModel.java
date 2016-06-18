package model;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;

import java.util.ArrayList;
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
    private ArrayList<IfStmt> expIfConditions = new ArrayList<>();
    private String switchCase = "";
    private ArrayList<SwitchStmt> expSwith = new ArrayList<>();
    private String loopFor = "";
    private ArrayList<ForStmt> expLoopFor = new ArrayList<>();
    private ArrayList<WhileStmt> expLoopWhile = new ArrayList<>();
    private String loopWhile = "";

    private ArrayList<CatchClause> expCatch = new ArrayList<>();

    private ArrayList<DoStmt> expLoopDo = new ArrayList<>();
    private String loopDo = "";

    private String methodInvocation = "";
    private ArrayList<MethodCallExpr> expMethodInvocation = new ArrayList<>();
    private String assign = "";
    private ArrayList<AssignExpr> expAssign = new ArrayList<>();
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
        this.expLoopFor.add(expLoopFor);
    }

    public void setExpLoopWhile(WhileStmt expLoopWhile) {
        this.expLoopWhile.add(expLoopWhile);
    }

    public ArrayList<WhileStmt> getExpLoopWhile() {
        return expLoopWhile;
    }

    public ArrayList<ForStmt> getExpLoopFor() {
        return expLoopFor;
    }

    public ArrayList<DoStmt> getExpLoopDo() {
        return expLoopDo;
    }

    public void setExpLoopDo(DoStmt expLoopDo) {
        this.expLoopDo.add(expLoopDo);
    }

    public void setExpAssign(AssignExpr expAssign) {
        this.expAssign.add(expAssign);
    }

    public ArrayList<AssignExpr> getExpAssign() {
        return expAssign;
    }

    public void setExpMethodInvocation(MethodCallExpr expMethodInvocation) {
        this.expMethodInvocation.add(expMethodInvocation);
    }

    public ArrayList<MethodCallExpr> getExpMethodInvocation() {
        return expMethodInvocation;
    }

    public void setExpIfCondition(IfStmt expIfCondition) {
        this.expIfConditions.add(expIfCondition);
    }

    public ArrayList<IfStmt> getExpIfConditions() {
        return expIfConditions;
    }

    public void setExpSwitch(SwitchStmt expSwitch) {
        this.expSwith.add(expSwitch);
    }

    public ArrayList<SwitchStmt> getExpSwith() {
        return expSwith;
    }

    public void setExpCatch(CatchClause expCatch) {
        this.expCatch.add(expCatch);
    }

    public ArrayList<CatchClause> getExpCatch() {
        return expCatch;
    }
}
