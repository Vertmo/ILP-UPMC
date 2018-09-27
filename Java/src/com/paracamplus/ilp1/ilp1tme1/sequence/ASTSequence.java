package com.paracamplus.ilp1.ilp1tme1.sequence;

import com.paracamplus.ilp1.ast.ASTsequence;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

public class ASTSequence extends ASTsequence implements IASTSequence {
    public ASTSequence(IASTexpression[] expressions) {
        super(expressions);
    }

    @Override
    public IASTexpression[] getAllButLastInstructions() throws EvaluationException {
        IASTexpression[] exprs = new IASTexpression[expressions.length-1];
        for(int i=0; i<expressions.length-1; i++) exprs[i] = expressions[i];
        return exprs;
    }
}
