package com.paracamplus.ilp4.ilp4tme10.compiler;

import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;

public class GlobalVariableCollector extends com.paracamplus.ilp4.compiler.GlobalVariableCollector
    implements IASTCvisitor<Set<IASTCglobalVariable>, Set<IASTCglobalVariable>, CompilationException> {

    @Override
    public Set<IASTCglobalVariable> visit(IASTexists iast, Set<IASTCglobalVariable> data) throws CompilationException {
        return data;
    }

    @Override
    public Set<IASTCglobalVariable> visit(IASTdefined iast, Set<IASTCglobalVariable> data) throws CompilationException {
        return data;
    }
}
