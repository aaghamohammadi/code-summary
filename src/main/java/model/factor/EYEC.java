package model.factor;

public enum EYEC {
    RETURNVALUE(1.0),
    METHODNAME(4.2),
    ERRORHANDLER(1.0),
    ENDINGUNIT(1.0),
    METHODINVOCATION(1.4),
    PARAMETERS(1.0),
    ASSIGNMENT(1.0),
    BRANCHES(0.6),
    LOOPSFOR(0.6),
    LOOPSWHILE(0.6),
    LOCALVARIABLE(1.0);


    private final double value;

    EYEC(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
