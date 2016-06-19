package controller;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import core.scanner.LexicalAnalyzer;
import model.MethodModel;
import model.ProgramModel;
import model.factor.AvgFactor;
import model.factor.EYEC;
import model.factor.TypeWord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MethodsController {
    private FileInputStream in;
    private CompilationUnit cu;

    public MethodsController(String path, String mode) throws IOException {
        try {
            in = new FileInputStream(path);
            cu = JavaParser.parse(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }

        new MethodVisitor(mode).visit(cu, null);
    }
}

class MethodVisitor extends VoidVisitorAdapter {
    private boolean functionParsing = false;
    private String mode;
    private MethodModel method;
    private ProgramModel programModel = ProgramModel.getInstance();

    public MethodVisitor(String mode) {
        this.mode = mode;
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        method = new MethodModel();
        method.setMethodName(n.getName());
        method.setMethodBody(LexicalAnalyzer.getInstance().getTokens(n.getName() + " " + n.getParameters() + " " + n.getBody().toStringWithoutComments()));
        method.setParameter(LexicalAnalyzer.getInstance().getTokens(n.getParameters().toString()));
        method.setExpParameters(n.getParameters());
        method.initDictionary();
        switch (mode) {
            case "EYEC":
                method.setDictionary(n.getName(), EYEC.METHODNAME.getValue(), TypeWord.METHODNAME.getValue());
                for (String s : method.getParameters().split(" ")) {
                    method.setDictionary(s, EYEC.PARAMETERS.getValue(), TypeWord.PARAMETERS.getValue());
                }
                break;
            case "AvgFactor":
                method.setDictionary(n.getName(), AvgFactor.METHODNAME.getValue(), TypeWord.METHODNAME.getValue());
                for (String s : method.getParameters().split(" ")) {
                    method.setDictionary(s, AvgFactor.PARAMETERS.getValue(), TypeWord.PARAMETERS.getValue());
                }
                break;
        }

        functionParsing = true;
        super.visit(n, arg);

        programModel.addMethod(method);
        functionParsing = false;
    }

    @Override
    public void visit(ReturnStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setReturnValue(LexicalAnalyzer.getInstance().getTokens(n.toString()));
        method.setExpReturnValue(n);
        switch (mode) {
            case "EYEC":
                for (String s : method.getReturnValue().split(" ")) {
                    method.setDictionary(s, EYEC.RETURNVALUE.getValue(), TypeWord.RETURNVALUE.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getReturnValue().split(" ")) {
                    method.setDictionary(s, AvgFactor.RETURNVALUE.getValue(), TypeWord.RETURNVALUE.getValue());
                }
                break;
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(IfStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setIfCondition(LexicalAnalyzer.getInstance().getTokens(n.toString()));
        method.setExpIfCondition(n);
        switch (mode) {
            case "EYEC":
                for (String s : method.getIfCondition().split(" ")) {
                    method.setDictionary(s, EYEC.BRANCHES.getValue(), TypeWord.IF.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getIfCondition().split(" ")) {
                    method.setDictionary(s, AvgFactor.BRANCHES.getValue(), TypeWord.IF.getValue());
                }
                break;
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(ForStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setLoopFor(LexicalAnalyzer.getInstance().getTokens(n.getInit().toString() + " " + n.getCompare().toString() + " " + n.getUpdate().toString()));
        method.setExpLoopFor(n);
        switch (mode) {
            case "EYEC":
                for (String s : method.getLoopFor().split(" ")) {
                    method.setDictionary(s, EYEC.LOOPSFOR.getValue(), TypeWord.LOOPSFOR.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getLoopFor().split(" ")) {
                    method.setDictionary(s, AvgFactor.LOOPSFOR.getValue(), TypeWord.LOOPSFOR.getValue());
                }
                break;
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(WhileStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setLoopWhile(LexicalAnalyzer.getInstance().getTokens(n.getCondition().toString()));
        method.setExpLoopWhile(n);
        switch (mode) {
            case "EYEC":
                for (String s : method.getLoopWhile().split(" ")) {
                    method.setDictionary(s, EYEC.LOOPSWHILE.getValue(), TypeWord.LOOPSWHILE.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getLoopWhile().split(" ")) {
                    method.setDictionary(s, AvgFactor.LOOPSWHILE.getValue(), TypeWord.LOOPSWHILE.getValue());
                }
                break;
        }

        super.visit(n, arg);
    }

    @Override
    public void visit(SwitchStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setSwitchCase(LexicalAnalyzer.getInstance().getTokens(n.getSelector().toString()));
        method.setExpSwitch(n);
        switch (mode) {
            case "EYEC":
                for (String s : method.getSwitchCase().split(" ")) {
                    method.setDictionary(s, EYEC.BRANCHES.getValue(), TypeWord.SWITCH.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getSwitchCase().split(" ")) {
                    method.setDictionary(s, AvgFactor.BRANCHES.getValue(), TypeWord.SWITCH.getValue());
                }
                break;
        }

        super.visit(n, arg);
    }

    @Override
    public void visit(DoStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setLoopDo(LexicalAnalyzer.getInstance().getTokens(n.getCondition().toString()));
        method.setExpLoopDo(n);
        switch (mode) {
            case "EYEC":
                for (String s : method.getLoopDo().split(" ")) {
                    method.setDictionary(s, EYEC.LOOPSWHILE.getValue(), TypeWord.LOOPSDO.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getLoopDo().split(" ")) {
                    method.setDictionary(s, AvgFactor.LOOPSWHILE.getValue(), TypeWord.LOOPSDO.getValue());
                }
                break;
        }

        super.visit(n, arg);
    }

    @Override
    public void visit(VariableDeclarator n, Object arg) {
        if (!functionParsing)
            return;
        method.setLocalVariable(LexicalAnalyzer.getInstance().getTokens(n.getId().toString()));
        switch (mode) {
            case "EYEC":
                for (String s : method.getLocalVariable().split(" ")) {
                    method.setDictionary(s, EYEC.LOCALVARIABLE.getValue(), TypeWord.LOCALVARIABLE.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getLocalVariable().split(" ")) {
                    method.setDictionary(s, AvgFactor.LOCALVARIABLE.getValue(), TypeWord.LOCALVARIABLE.getValue());
                }
                break;
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(AssignExpr n, Object arg) {
        if (!functionParsing)
            return;
        method.setAssign(LexicalAnalyzer.getInstance().getTokens(n.getValue().toString()));
        method.setExpAssign(n);
        switch (mode) {
            case "EYEC":
                for (String s : method.getAssign().split(" ")) {
                    method.setDictionary(s, EYEC.ASSIGNMENT.getValue(), TypeWord.ASSIGNMENT.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getAssign().split(" ")) {
                    method.setDictionary(s, AvgFactor.ASSIGNMENT.getValue(), TypeWord.ASSIGNMENT.getValue());
                }
                break;
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Object arg) {
        if (!functionParsing)
            return;
        method.setMethodInvocation(LexicalAnalyzer.getInstance().getTokens(n.toString()));
        method.setExpMethodInvocation(n);
        switch (mode) {
            case "EYEC":
                for (String s : method.getMethodInvocation().split(" ")) {
                    method.setDictionary(s, EYEC.METHODINVOCATION.getValue(), TypeWord.METHODINVOCATION.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : method.getMethodInvocation().split(" ")) {
                    method.setDictionary(s, AvgFactor.METHODINVOCATION.getValue(), TypeWord.METHODINVOCATION.getValue());
                }
                break;
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(CatchClause n, Object arg) {
        if (!functionParsing)
            return;
        method.setExpCatch(n);
        switch (mode) {
            case "EYEC":
                for (String s : LexicalAnalyzer.getInstance().getTokens(n.getExcept().toString() + " " + n.getCatchBlock().toString()).split(" ")) {
                    method.setDictionary(s, EYEC.ERRORHANDLER.getValue(), TypeWord.ERRORHANDLER.getValue());
                }
                break;
            case "AvgFactor":
                for (String s : LexicalAnalyzer.getInstance().getTokens(n.getExcept().toString() + " " + n.getCatchBlock().toString()).split(" ")) {
                    method.setDictionary(s, AvgFactor.ERRORHANDLER.getValue(), TypeWord.ERRORHANDLER.getValue());
                }
                break;
        }
        super.visit(n, arg);
    }
}
