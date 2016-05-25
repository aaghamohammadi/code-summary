package controller;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

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

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        System.out.println(n.getName());
        super.visit(n, arg);
    }
}
