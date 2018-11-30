package com.paracamplus.ilp3.ilp3tme7.compiler.interfaces;

import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTvisitor;

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable>
    extends IASTvisitor<Result, Data, Anomaly>, com.paracamplus.ilp3.compiler.interfaces.IASTCvisitor<Result, Data, Anomaly> {
    Result visit(IASTCglobalCostart iast, Data data) throws Anomaly;
}
