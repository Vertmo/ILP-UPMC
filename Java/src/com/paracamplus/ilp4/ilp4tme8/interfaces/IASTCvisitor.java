package com.paracamplus.ilp4.ilp4tme8.interfaces;

import com.paracamplus.ilp4.interfaces.IASTvisitor;

public interface IASTCvisitor<Result, Data, Anomaly extends Throwable>
    extends com.paracamplus.ilp4.interfaces.IASTvisitor<Result, Data, Anomaly>,
            com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitor<Result, Data, Anomaly> {
}
