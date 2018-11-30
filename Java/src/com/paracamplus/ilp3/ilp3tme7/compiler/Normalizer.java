package com.paracamplus.ilp3.ilp3tme7.compiler;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp3.compiler.interfaces.IASTCcodefinitions;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionInvocation;
import com.paracamplus.ilp3.compiler.interfaces.IASTClocalFunctionVariable;
import com.paracamplus.ilp3.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCglobalCostart;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTcostart;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTvisitor;

public class Normalizer extends com.paracamplus.ilp3.compiler.normalizer.Normalizer
		implements IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {

	public Normalizer(INormalizationFactory factory) {
		super(factory);
	}

	@Override
	public IASTexpression visit(IASTcostart iast, INormalizationEnvironment data) throws CompilationException {
      IASTexpression[] args = new IASTexpression[iast.getArguments().length];
      for(int i = 0; i < iast.getArguments().length; i++) {
          args[i] = iast.getArguments()[i].accept(this, data);
      }
      return new ASTCglobalCostart(iast.getFunction().accept(this, data), args);
	}

}
