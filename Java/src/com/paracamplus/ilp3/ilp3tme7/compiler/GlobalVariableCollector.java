package com.paracamplus.ilp3.ilp3tme7.compiler;

import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCglobalCostart;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTcostart;

public class GlobalVariableCollector extends com.paracamplus.ilp3.compiler.GlobalVariableCollector
		implements IASTCvisitor<Set<IASTCglobalVariable>, Set<IASTCglobalVariable>, CompilationException> {

    @Override
    public Set<IASTCglobalVariable> visit(IASTcostart iast, Set<IASTCglobalVariable> result) throws CompilationException {
        result = iast.getFunction().accept(this, result);
        for(IASTexpression arg: iast.getArguments()) {
            result = arg.accept(this, result);
        }
        return result;
    }

	@Override
	public Set<IASTCglobalVariable> visit(IASTCglobalCostart iast, Set<IASTCglobalVariable> result) throws CompilationException {
      return visit((IASTcostart) iast, result);
	}
}
