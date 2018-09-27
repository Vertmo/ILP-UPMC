package com.paracamplus.ilp1.ilp1tme1.sequence;

import com.paracamplus.ilp1.interfaces.IASTexpression;

public class ASTfactory extends com.paracamplus.ilp1.ast.ASTfactory {
    public IASTSequence newSequence(IASTexpression[] asts) {
        return new ASTSequence(asts);
    }

}
