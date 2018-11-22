package com.paracamplus.ilp2.ilp2tme6;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interpreter.Function;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTvisitor;

public class UsedAnalysis implements IASTvisitor<Boolean, IASTvariable, CompilationException> {

    @Override
    public Boolean visit(IASTalternative iast, IASTvariable data) throws CompilationException {
        return iast.getCondition().accept(this, data)
            || iast.getConsequence().accept(this, data)
            || (iast.isTernary() && iast.getAlternant().accept(this, data));
    }

    @Override
    public Boolean visit(IASTbinaryOperation iast, IASTvariable data) throws CompilationException {
        return iast.getLeftOperand().accept(this, data)
            || iast.getRightOperand().accept(this, data);
    }

    @Override
    public Boolean visit(IASTblock iast, IASTvariable data) throws CompilationException {
        for(IASTbinding b: iast.getBindings()) {
            if(b.getInitialisation().accept(this, data)) return true;
        }
        return iast.getBody().accept(this, data);
    }

    @Override
    public Boolean visit(IASTboolean iast, IASTvariable data) throws CompilationException {
        return false;
    }

    @Override
    public Boolean visit(IASTfloat iast, IASTvariable data) throws CompilationException {
        return false;
    }

    @Override
    public Boolean visit(IASTinteger iast, IASTvariable data) throws CompilationException {
        return false;
    }

    @Override
    public Boolean visit(IASTinvocation iast, IASTvariable data) throws CompilationException {
        for(IASTexpression e: iast.getArguments()) {
            if(e.accept(this, data)) return true;
        }
        return iast.getFunction().accept(this, data);
    }

    @Override
    public Boolean visit(IASTsequence iast, IASTvariable data) throws CompilationException {
        for(IASTexpression e: iast.getExpressions()) {
            if(e.accept(this, data)) return true;
        }
        return false;
    }

    @Override
    public Boolean visit(IASTstring iast, IASTvariable data) throws CompilationException {
        return false;
    }

    @Override
    public Boolean visit(IASTunaryOperation iast, IASTvariable data) throws CompilationException {
        return iast.getOperand().accept(this, data);
    }

    @Override
    public Boolean visit(IASTvariable iast, IASTvariable data) throws CompilationException {
        return iast.getName().equals(data.getName());
    }

    @Override
    public Boolean visit(IASTassignment iast, IASTvariable data) throws CompilationException {
        return iast.getVariable().getName().equals(data.getName())
            || iast.getExpression().accept(this, data);
    }

    @Override
    public Boolean visit(IASTloop iast, IASTvariable data) throws CompilationException {
        return iast.getCondition().accept(this, data)||iast.getBody().accept(this, data);
    }
}
