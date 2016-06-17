package model.factor;

public enum TypeWord {
    RETURNVALUE("RetrunValue"),
    METHODNAME("MethodName"),
    ERRORHANDLER("ErrorHandler"),
    ENDINGUNIT("EndingUnit"),
    METHODINVOCATION("MethodInvocation"),
    PARAMETERS("Parameters"),
    ASSIGNMENT("Assignment"),
    BRANCHES("Branchees"),
    LOOPSFOR("LoopsFor"),
    LOOPSWHILE("LoopsWhile"),
    LOOPSDO("LoopsDo"),
    LOCALVARIABLE("LocalVariable");
    private final String value;

    TypeWord(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
