package com.paracamplus.ilp2.ilp2tme4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.interfaces.IASTCvariable;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTCvisitor;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTunless;

public class FreeVariableCollector extends com.paracamplus.ilp2.compiler.FreeVariableCollector
implements IASTCvisitor<Void, Set<IASTClocalVariable>, CompilationException> {

    public FreeVariableCollector(IASTCprogram program) {
        super(program);
    }

    @Override
    public IASTCprogram analyze () throws CompilationException {
        for ( IASTCfunctionDefinition ifd : ((IASTCprogram) program).getFunctionDefinitions() ) {
            Set<IASTClocalVariable> newvars = new HashSet<>();
            visit(ifd, newvars);
        }
        Set<IASTClocalVariable> newvars = new HashSet<>();
        ((IASTCprogram) program).getBody().accept(this, newvars);
        return (IASTCprogram) program;
    }

    @Override
    public Void visit(IASTunless iast, Set<IASTClocalVariable> variables) throws CompilationException {
        iast.getCondition().accept(this, variables);
        iast.getBody().accept(this, variables);
        return null;
    }
}
