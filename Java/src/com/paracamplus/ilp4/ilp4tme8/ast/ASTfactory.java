package com.paracamplus.ilp4.ilp4tme8.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTfactory;

public class ASTfactory extends com.paracamplus.ilp4.ast.ASTfactory implements IASTfactory {

	@Override
	public IASTexpression newReadProperty(IASTexpression fieldExpr, IASTexpression object) {
      return new ASTreadProperty(fieldExpr, object);
	}

	@Override
	public IASTexpression newWriteProperty(IASTexpression fieldExpr, IASTexpression object, IASTexpression value) {
      return new ASTwriteProperty(fieldExpr, object, value);
	}

	@Override
	public IASTexpression newHasProperty(IASTexpression fieldExpr, IASTexpression object) {
      return new ASThasProperty(fieldExpr, object);
	}
}
