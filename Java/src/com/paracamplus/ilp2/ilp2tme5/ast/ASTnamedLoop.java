package com.paracamplus.ilp2.ilp2tme5.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.ast.ASTloop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitor;

public class ASTnamedLoop extends ASTloop implements IASTnamedLoop {
    private String name;

    public ASTnamedLoop(IASTexpression condition, IASTexpression body, String name) {
        super(condition, body);
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public <Result, Data, Anomaly extends Throwable> Result accept(
        com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor, Data data) throws Anomaly {
        return ((IASTvisitor <Result, Data, Anomaly>) visitor).visit(this, data);
    }
}
