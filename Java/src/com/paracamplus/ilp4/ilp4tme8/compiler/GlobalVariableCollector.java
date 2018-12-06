package com.paracamplus.ilp4.ilp4tme8.compiler;

import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;

public class GlobalVariableCollector extends com.paracamplus.ilp4.compiler.GlobalVariableCollector
    implements IASTCvisitor<Set<IASTCglobalVariable>, Set<IASTCglobalVariable>, CompilationException> {

    @Override
    public Set<IASTCglobalVariable> visit(IASTreadProperty iast, Set<IASTCglobalVariable> result) throws CompilationException {
        result = iast.getFieldExpr().accept(this, result);
        result = iast.getTarget().accept(this, result);
        return result;
    }

    @Override
    public Set<IASTCglobalVariable> visit(IASTwriteProperty iast, Set<IASTCglobalVariable> result) throws CompilationException {
        result = iast.getFieldExpr().accept(this, result);
        result = iast.getTarget().accept(this, result);
        result = iast.getValue().accept(this, result);
        return result;
    }

    @Override
    public Set<IASTCglobalVariable> visit(IASThasProperty iast, Set<IASTCglobalVariable> result) throws CompilationException {
        result = iast.getFieldExpr().accept(this, result);
        result = iast.getTarget().accept(this, result);
        return result;
    }
}
