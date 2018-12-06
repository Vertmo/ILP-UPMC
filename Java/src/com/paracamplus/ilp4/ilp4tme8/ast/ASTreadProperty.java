package com.paracamplus.ilp4.ilp4tme8.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;

public class ASTreadProperty implements IASTreadProperty {
    private IASTexpression field;
    private IASTexpression target;

    public ASTreadProperty(IASTexpression field, IASTexpression object) {
        this.field = field;
        target = object;
    }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
                                                                   Data data) throws Anomaly {
        return ((com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
    }

    @Override
    public IASTexpression getTarget() {
        return target;
    }

    @Override
    public IASTexpression getFieldExpr() {
        return field;
    }
}
