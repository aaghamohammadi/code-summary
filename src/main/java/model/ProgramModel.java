package model;

import java.util.ArrayList;

public class ProgramModel {
    private static ProgramModel instance = null;
    private ArrayList<MethodModel> methodModel = new ArrayList<MethodModel>();

    protected ProgramModel() {

    }

    public static ProgramModel getInstance() {
        if (instance == null)
            instance = new ProgramModel();
        return instance;
    }

    public void addMethod(MethodModel m) {
        methodModel.add(m);
    }

    public ArrayList<MethodModel> getMethodModel() {
        return methodModel;
    }
}
