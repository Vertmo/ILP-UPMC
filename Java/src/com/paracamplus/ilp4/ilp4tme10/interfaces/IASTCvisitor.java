package com.paracamplus.ilp4.ilp4tme10.interfaces;

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable>
    extends IASTvisitor<Result, Data, Anomaly>,
            com.paracamplus.ilp4.compiler.interfaces.IASTCvisitor<Result, Data, Anomaly> {
}
