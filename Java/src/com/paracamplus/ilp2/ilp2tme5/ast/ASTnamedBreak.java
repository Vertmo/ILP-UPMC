package com.paracamplus.ilp2.ilp2tme5.ast;

import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitor;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedBreak;

public class ASTnamedBreak extends ASTbreak implements IASTnamedBreak {
    private String name;

    public ASTnamedBreak(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(
        com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor, Data data) throws Anomaly {
        return ((IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
    }

}
