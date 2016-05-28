package model;

import com.github.javaparser.ast.body.Parameter;

import java.util.List;

public class MethodModel {
    private String methodName;
    private String methodBody;
    private String returnValue;
    private List<Parameter> parameters;

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMethodBody(String methodBody) {
        this.methodBody = methodBody;
    }

    public void setParameter(List<Parameter> parameters) {
        this.parameters = parameters;

    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public String getMethodBody() {
        return methodBody;
    }

    public String getMethodName() {

        return methodName;
    }
}
