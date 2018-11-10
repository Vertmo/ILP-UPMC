package com.paracamplus.ilp2.ilp2tme6;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IAST;
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
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.paracamplus.ilp2.interfaces.IASTvisitor;

public class CopyTransform<Data> implements IASTvisitor<IAST, Data, CompilationException> {
    protected IASTfactory factory;

    public CopyTransform(IASTfactory factory) {
        this.factory = factory;
    }

    public IASTprogram visit(IASTprogram iast, Data data) throws CompilationException {
        IASTfunctionDefinition[] definitions = new IASTfunctionDefinition[iast.getFunctionDefinitions().length];
        for(int i = 0; i < iast.getFunctionDefinitions().length; i++) {
            IASTfunctionDefinition def = iast.getFunctionDefinitions()[i];
            IASTvariable vars[] = new IASTvariable[def.getVariables().length];
            for(int j = 0; j < def.getVariables().length; j++) {
                vars[j] = (IASTvariable) def.getVariables()[j].accept(this, data);
            }
            definitions[i] = factory.newFunctionDefinition(def.getFunctionVariable(),
                                                           vars,
                                                           (IASTexpression) def.getBody().accept(this, data));
        }
        return factory.newProgram(definitions, (IASTexpression) iast.getBody().accept(this, data));
    }

    @Override
    public IAST visit(IASTinteger iast, Data data) throws CompilationException {
        return factory.newIntegerConstant(iast.getValue().toString());
    }

    @Override
    public IAST visit(IASTalternative iast, Data data) throws CompilationException {
        IASTexpression alternant = null;
        if(iast.isTernary()) {
            alternant = (IASTexpression) iast.getAlternant().accept(this, data);
        }
        return factory.newAlternative((IASTexpression) iast.getCondition().accept(this, data),
                                      (IASTexpression) iast.getConsequence().accept(this, data),
                                      alternant);
    }

    @Override
    public IAST visit(IASTbinaryOperation iast, Data data) throws CompilationException {
        return factory.newBinaryOperation(iast.getOperator(),
                                          (IASTexpression) iast.getLeftOperand().accept(this, data),
                                          (IASTexpression) iast.getRightOperand().accept(this, data));
    }

    @Override
    public IAST visit(IASTblock iast, Data data) throws CompilationException {
        IASTbinding[] bindings = new IASTbinding[iast.getBindings().length];
        for(int i = 0; i < iast.getBindings().length; i++) {
            IASTbinding bin = iast.getBindings()[i];
            bindings[i] = factory.newBinding((IASTvariable) bin.getVariable().accept(this, data),
                                             (IASTexpression) bin.getInitialisation().accept(this, data));
        }
        return factory.newBlock(bindings,
                                (IASTexpression) iast.getBody().accept(this, data));
    }

    @Override
    public IAST visit(IASTboolean iast, Data data) throws CompilationException {
        return factory.newBooleanConstant(iast.getValue().toString());
    }

    @Override
    public IAST visit(IASTfloat iast, Data data) throws CompilationException {
        return factory.newFloatConstant(iast.getValue().toString());
    }

    @Override
    public IAST visit(IASTinvocation iast, Data data) throws CompilationException {
        IASTexpression[] args = new IASTexpression[iast.getArguments().length];
        for(int i = 0; i < iast.getArguments().length; i++) {
            args[i] = (IASTexpression) iast.getArguments()[i].accept(this, data);
        }
        return factory.newInvocation((IASTexpression) iast.getFunction().accept(this, data),
                                     args);
    }

    @Override
    public IAST visit(IASTsequence iast, Data data) throws CompilationException {
        IASTexpression[] exprs = new IASTexpression[iast.getExpressions().length];
        for(int i = 0; i < iast.getExpressions().length; i++) {
            exprs[i] = (IASTexpression) iast.getExpressions()[i].accept(this, data);
        }
        return factory.newSequence(exprs);
    }

    @Override
    public IAST visit(IASTstring iast, Data data) throws CompilationException {
        return factory.newStringConstant(iast.getValue());
    }

    @Override
    public IAST visit(IASTunaryOperation iast, Data data) throws CompilationException {
        return factory.newUnaryOperation(iast.getOperator(),
                                         (IASTexpression) iast.getOperand().accept(this, data));
    }

    @Override
    public IAST visit(IASTvariable iast, Data data) throws CompilationException {
        return factory.newVariable(iast.getName());
    }

    @Override
    public IAST visit(IASTassignment iast, Data data) throws CompilationException {
        return factory.newAssignment((IASTvariable) iast.getVariable().accept(this, data),
                                     (IASTexpression) iast.getExpression().accept(this, data));
    }

    @Override
    public IAST visit(IASTloop iast, Data data) throws CompilationException {
        return factory.newLoop((IASTexpression) iast.getCondition().accept(this, data),
                               (IASTexpression) iast.getBody().accept(this, data));
    }
}
