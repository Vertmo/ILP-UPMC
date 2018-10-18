package com.paracamplus.ilp2.ilp2tme4;

import com.paracamplus.ilp1.ast.ASToperator;
import com.paracamplus.ilp1.ast.ASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interpreter.operator.Operator;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTunless;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTvisitor;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTloop;

public class UnlessRemover implements IASTvisitor<IASTexpression, Void, ParseException> {

    @Override
    public IASTexpression visit(IASTassignment iast, Void data) throws ParseException {
        return new ASTfactory().newAssignment((IASTvariable) iast.getVariable().accept(this, data), iast.getExpression().accept(this, data));
    }

    @Override
    public IASTexpression visit(IASTalternative iast, Void data) throws ParseException {
        if(iast.isTernary())
            return new ASTfactory().newAlternative(iast.getCondition().accept(this, data),
                                                   iast.getConsequence().accept(this, data),
                                                   iast.getAlternant().accept(this, data));
        else
            return new ASTfactory().newAlternative(iast.getCondition().accept(this, data),
                                                   iast.getConsequence().accept(this, data),
                                                   null);
    }

    @Override
    public IASTexpression visit(IASTfloat iast, Void data) throws ParseException {
        return iast;
    }

    @Override
    public IASTexpression visit(IASTboolean iast, Void data) throws ParseException {
        return iast;
    }

    @Override
    public IASTexpression visit(IASTblock iast, Void data) throws ParseException {
        return new ASTfactory().newBlock(iast.getBindings(), iast.getBody().accept(this, data));
    }

    @Override
    public IASTexpression visit(IASTbinaryOperation iast, Void data) throws ParseException {
        return new ASTfactory().newBinaryOperation(iast.getOperator(),
                                                   iast.getLeftOperand().accept(this, data),
                                                   iast.getRightOperand().accept(this, data));
    }

    @Override
    public IASTexpression visit(IASTloop iast, Void data) throws ParseException {
        return new ASTfactory().newLoop(iast.getCondition().accept(this, data), iast.getBody().accept(this, data));
    }

    @Override
    public IASTexpression visit(IASTinteger iast, Void data) throws ParseException {
        return iast;
    }

    @Override
    public IASTexpression visit(IASTunless iast, Void data) throws ParseException {
        return new ASTfactory().newAlternative(new ASTunaryOperation(new ASToperator("!"), iast.getCondition().accept(this, data)),
                                               iast.getBody().accept(this, data),
                                               null);
    }

    @Override
    public IASTexpression visit(IASTinvocation iast, Void data) throws ParseException {
        int n = iast.getArguments().length;
        IASTexpression[] newArgs = new IASTexpression[n];
        for(int i = 0; i < n; i++) {
            newArgs[i] = iast.getArguments()[i].accept(this, data);
        }
        return new ASTfactory().newInvocation(iast.getFunction().accept(this, data), newArgs);
    }

    @Override
    public IASTexpression visit(IASTsequence iast, Void data) throws ParseException {
        int n = iast.getExpressions().length;
        IASTexpression[] newS = new IASTexpression[n];
        for(int i = 0; i < n; i++) {
            newS[i] = iast.getExpressions()[i].accept(this, data);
        }
        return new ASTfactory().newSequence(newS);
    }

    @Override
    public IASTexpression visit(IASTstring iast, Void data) throws ParseException {
        return iast;
    }

    @Override
    public IASTexpression visit(IASTunaryOperation iast, Void data) throws ParseException {
        return new ASTfactory().newUnaryOperation(iast.getOperator(), iast.getOperand().accept(this, data));
    }

    @Override
    public IASTexpression visit(IASTvariable iast, Void data) throws ParseException {
        return iast;
    }

}
