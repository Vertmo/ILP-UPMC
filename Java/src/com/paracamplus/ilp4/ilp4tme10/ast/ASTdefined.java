package com.paracamplus.ilp4.ilp4tme10.ast;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;

public class ASTdefined extends ASTexists implements IASTdefined {
    public ASTdefined(IASTvariable var) {
        super(var);
    }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
                                                                   Data data) throws Anomaly {
        return ((com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
    }

    @Override
    public IASTvariable getVariable() { return var; }
}
