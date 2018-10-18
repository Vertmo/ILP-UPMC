package com.paracamplus.ilp2.ilp2tme4;

import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTCvisitor;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTunless;

public class GlobalVariableCollector extends com.paracamplus.ilp2.compiler.GlobalVariableCollector
    implements IASTCvisitor<Set<IASTCglobalVariable>,
                                Set<IASTCglobalVariable>,
                                CompilationException> {

    public Set<IASTCglobalVariable> analyze(IASTCprogram program)
        throws CompilationException {
        for ( IASTCfunctionDefinition ifd : program.getFunctionDefinitions() ) {
            result = ifd.getBody().accept(this, result);
        }
        result = program.getBody().accept(this, result);
        return result;
    }

    @Override
    public Set<IASTCglobalVariable> visit(IASTunless iast, Set<IASTCglobalVariable> result) throws CompilationException {
        result = iast.getCondition().accept(this, result);
        result = iast.getBody().accept(this, result);
        return result;
    }
}
