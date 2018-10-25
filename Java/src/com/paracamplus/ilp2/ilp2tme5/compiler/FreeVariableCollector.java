package com.paracamplus.ilp2.ilp2tme5.compiler;

import java.util.HashSet;
import java.util.Set;

import com.paracamplus.ilp2.compiler.interfaces.IASTCfunctionDefinition;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp2.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTCvisitor;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedBreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedContinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedLoop;

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
    public Void visit(IASTbreak iast, Set<IASTClocalVariable> variables) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTcontinue iast, Set<IASTClocalVariable> variables) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTnamedLoop iast, Set<IASTClocalVariable> variables) throws CompilationException {
        iast.getCondition().accept(this, variables);
        return null;
    }

    @Override
    public Void visit(IASTnamedBreak iast, Set<IASTClocalVariable> variables) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTnamedContinue iast, Set<IASTClocalVariable> variables) throws CompilationException {
        return null;
    }
}
