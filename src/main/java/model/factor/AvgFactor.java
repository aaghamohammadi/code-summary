package model.factor;

public enum AvgFactor {
    RETURNVALUE(3.69),
    METHODNAME(3.69),
    ERRORHANDLER(3.21),
    ENDINGUNIT(3.18),
    METHODINVOCATION(2.94),
    PARAMETERS(2.69),
    ASSIGNMENT(2.44),
    BRANCHES(2.08),
    LOOPS(1.97),
    LOCALVARIABLE(1.9);


    private final double value;

    AvgFactor(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

