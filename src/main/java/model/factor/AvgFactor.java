package model.factor;

public enum AvgFactor {
    RETURNVALUE(3.69),
    METHODNAME(3.69),
    ERRORHANDLER(2.21),
    ENDINGUNIT(3.18),
    METHODINVOCATION(1.94),
    PARAMETERS(1.69),
    ASSIGNMENT(1.04),
    BRANCHES(1.98),
    LOOPSFOR(1.57),
    LOOPSWHILE(1.47),
    LOCALVARIABLE(1.0);


    private final double value;

    AvgFactor(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

