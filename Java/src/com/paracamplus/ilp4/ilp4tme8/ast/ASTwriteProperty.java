package com.paracamplus.ilp4.ilp4tme8.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;

public class ASTwriteProperty implements IASTwriteProperty {
    private IASTexpression field;
    private IASTexpression target;
    private IASTexpression value;

    public ASTwriteProperty(IASTexpression field, IASTexpression object, IASTexpression value) {
        this.field = field;
        target = object;
        this.value = value;
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

    @Override
    public IASTexpression getValue() {
        return value;
    }
}
