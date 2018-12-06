package com.paracamplus.ilp4.ilp4tme8.compiler;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp4.ilp4tme8.ast.ASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.ast.ASTwriteProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;

public class Normalizer extends com.paracamplus.ilp4.compiler.normalizer.Normalizer
    implements IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {

    public Normalizer (INormalizationFactory factory,
                       IASTCclassDefinition objectClass ) {
        super(factory, objectClass);
	}

	@Override
	public IASTexpression visit(IASTreadProperty iast, INormalizationEnvironment data) throws CompilationException {
      return new ASTreadProperty(iast.getFieldExpr().accept(this, data), iast.getTarget().accept(this, data));
	}

	@Override
	public IASTexpression visit(IASTwriteProperty iast, INormalizationEnvironment data) throws CompilationException {
      return new ASTwriteProperty(iast.getFieldExpr().accept(this, data), iast.getTarget().accept(this, data),
                                  iast.getValue().accept(this, data));	}

	@Override
	public IASTexpression visit(IASThasProperty iast, INormalizationEnvironment data) throws CompilationException {
      return new ASThasProperty(iast.getFieldExpr().accept(this, data), iast.getTarget().accept(this, data));
	}
}
