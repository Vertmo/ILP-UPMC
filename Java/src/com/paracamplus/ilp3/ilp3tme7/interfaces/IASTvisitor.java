package com.paracamplus.ilp3.ilp3tme7.interfaces;

public interface IASTvisitor<Result, Data, Anomaly extends Throwable>
    extends com.paracamplus.ilp3.interfaces.IASTvisitor<Result, Data, Anomaly> {

    public Result visit(IASTcostart iast, Data data) throws Anomaly;
}
