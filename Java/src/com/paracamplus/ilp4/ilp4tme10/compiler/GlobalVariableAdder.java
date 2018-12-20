package com.paracamplus.ilp4.ilp4tme10.compiler;

import java.util.Collection;

import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldRead;
import com.paracamplus.ilp4.compiler.interfaces.IASTCfieldWrite;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
import com.paracamplus.ilp4.interfaces.IASTclassDefinition;
import com.paracamplus.ilp4.interfaces.IASTfieldRead;
import com.paracamplus.ilp4.interfaces.IASTfieldWrite;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp4.interfaces.IASTmethodDefinition;
import com.paracamplus.ilp4.interfaces.IASTprogram;
import com.paracamplus.ilp4.interfaces.IASTself;
import com.paracamplus.ilp4.interfaces.IASTsend;
import com.paracamplus.ilp4.interfaces.IASTsuper;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp3.compiler.interfaces.IASTClambda;
import com.paracamplus.ilp3.interfaces.IASTcodefinitions;
import com.paracamplus.ilp3.interfaces.IASTlambda;
import com.paracamplus.ilp3.interfaces.IASTtry;

public class GlobalVariableAdder implements IASTCvisitor<Void, Collection<IASTCglobalVariable>, CompilationException> {
    public Void visit(IASTCprogram iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        for(IASTfunctionDefinition def: iast.getFunctionDefinitions()) {
            def.getBody().accept(this, data);
        }
        for(IASTClambda l: iast.getClosureDefinitions()) {
            l.accept(this, data);
        }
        for(IASTclassDefinition def: iast.getClassDefinitions()) {
            for(IASTmethodDefinition defM: def.getProperMethodDefinitions()) {
                defM.getBody().accept(this, data);
            }
        }
        iast.getBody().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTinstantiation iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        for(IASTexpression e: iast.getArguments()) e.accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTfieldRead iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getTarget().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTself iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTsend iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        for(IASTexpression e: iast.getArguments()) e.accept(this, data);
        iast.getReceiver().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTsuper iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTfieldWrite iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getTarget().accept(this, data);
        iast.getValue().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTcodefinitions iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        for(IASTlambda l: iast.getFunctions()) l.accept(this, data);
        iast.getBody().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTlambda iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getBody().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTtry iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getBody().accept(this, data);
        if(iast.getCatcher() != null) iast.getCatcher().accept(this, data);
        if(iast.getFinallyer() != null) iast.getFinallyer().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTassignment iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getExpression().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTloop iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getCondition().accept(this, data);
        iast.getBody().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTalternative iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getCondition().accept(this, data);
        iast.getConsequence().accept(this, data);
        if(iast.isTernary()) iast.getAlternant().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTbinaryOperation iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getLeftOperand().accept(this, data);
        iast.getRightOperand().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTblock iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        for(IASTblock.IASTbinding bi: iast.getBindings()) {
            bi.getInitialisation().accept(this, data);
        }
        iast.getBody().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTboolean iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTfloat iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTinteger iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTinvocation iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        for(IASTexpression e: iast.getArguments()) e.accept(this, data);
        iast.getFunction().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTsequence iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        for(IASTexpression e: iast.getExpressions()) e.accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTstring iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTunaryOperation iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getOperand().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTvariable iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        return null;
    }

    @Override
    public Void visit(IASTexists iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.addGlobalVars(data);
        return null;
    }

    @Override
    public Void visit(IASTdefined iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.addGlobalVars(data);
        return null;
    }

    @Override
    public Void visit(IASTCfieldRead iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getTarget().accept(this, data);
        return null;
    }

    @Override
    public Void visit(IASTCfieldWrite iast, Collection<IASTCglobalVariable> data) throws CompilationException {
        iast.getTarget().accept(this, data);
        iast.getValue().accept(this, data);
        return null;
    }
}
