package controller;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import core.scanner.LexicalAnalyzer;
import model.MethodModel;
import model.ProgramModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MethodsController {
    private FileInputStream in;
    private CompilationUnit cu;

    public MethodsController(String path) throws IOException {
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

        new MethodVisitor().visit(cu, null);
    }
}

class MethodVisitor extends VoidVisitorAdapter {
    private boolean functionParsing = false;
    private MethodModel method;
    private ProgramModel programModel = ProgramModel.getInstance();

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        method = new MethodModel();

        method.setMethodName(n.getName());
        method.setMethodBody(n.getBody().toStringWithoutComments());
        method.setParameter(LexicalAnalyzer.getInstance().getTokens(n.getParameters().toString()));
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
        super.visit(n, arg);
    }

    @Override
    public void visit(IfStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setIfCondition(LexicalAnalyzer.getInstance().getTokens(n.toString()));
        super.visit(n, arg);
    }

    @Override
    public void visit(ForStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setLoopFor(LexicalAnalyzer.getInstance().getTokens(n.getInit().toString() + " " + n.getCompare().toString() + " " + n.getUpdate().toString()));
        super.visit(n, arg);
    }

    @Override
    public void visit(WhileStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setLoopWhile(LexicalAnalyzer.getInstance().getTokens(n.getCondition().toString()));
        super.visit(n, arg);
    }

    @Override
    public void visit(SwitchStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setSwitchCase(LexicalAnalyzer.getInstance().getTokens(n.getSelector().toString()));
        super.visit(n, arg);
    }

    @Override
    public void visit(DoStmt n, Object arg) {
        if (!functionParsing)
            return;
        method.setLoopDo(LexicalAnalyzer.getInstance().getTokens(n.getCondition().toString()));
        super.visit(n, arg);
    }

    @Override
    public void visit(VariableDeclarator n, Object arg) {
        if (!functionParsing)
            return;
        method.setLocalVariable(LexicalAnalyzer.getInstance().getTokens(n.getId().toString()));
        super.visit(n, arg);
    }

    @Override
    public void visit(AssignExpr n, Object arg) {
        if (!functionParsing)
            return;
        method.setAssign(LexicalAnalyzer.getInstance().getTokens(n.getValue().toString()));
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Object arg) {
        if (!functionParsing)
            return;
        method.setMethodInvocation(LexicalAnalyzer.getInstance().getTokens(n.toString()));
        super.visit(n, arg);
    }
}
