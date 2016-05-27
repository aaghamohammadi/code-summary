package controller;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
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
        method.setParameter(n.getParameters());
        functionParsing = true;
        super.visit(n, arg);

        programModel.addMethod(method);
        functionParsing = false;
    }

    @Override
    public void visit(ReturnStmt n, Object arg) {
        if (!functionParsing)
            return;
//        System.out.println(n);
        super.visit(n, arg);
    }

    @Override
    public void visit(IfStmt n, Object arg) {
        if (!functionParsing)
            return;
//        System.out.println("condition:" + n.getCondition());
        super.visit(n, arg);
    }

    @Override
    public void visit(ForStmt n, Object arg) {
        if (!functionParsing)
            return;
        super.visit(n, arg);
    }

    @Override
    public void visit(VariableDeclarationExpr n, Object arg) {
        if (!functionParsing)
            return;
//        System.out.println(n);
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Object arg) {
        if (!functionParsing)
            return;
//        System.out.println(n);
        super.visit(n, arg);
    }
}
