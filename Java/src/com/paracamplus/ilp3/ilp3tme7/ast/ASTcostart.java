package com.paracamplus.ilp3.ilp3tme7.ast;

import com.paracamplus.ilp1.ast.ASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTvisitor;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTcostart;

public class ASTcostart extends ASTinvocation implements IASTcostart {

	public ASTcostart(IASTexpression function, IASTexpression[] arguments) {
      super(function, arguments);
	}

	@Override
    public <Result, Data, Anomaly extends Throwable> Result accept(
            com.paracamplus.ilp1.interfaces.IASTvisitor<Result, Data, Anomaly> visitor,
            Data data) throws Anomaly {
        return ((IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
    }
}
