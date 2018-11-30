package com.paracamplus.ilp3.ilp3tme7.compiler;

import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp3.ilp3tme7.ast.ASTcostart;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCglobalCostart;

public class ASTCglobalCostart extends ASTcostart implements IASTCglobalCostart {
    public ASTCglobalCostart(IASTexpression function, IASTexpression[] arguments) {
        super(function, arguments);
    }

    @Override
    public IASTCglobalVariable getFunction() {
        return (IASTCglobalVariable) super.getFunction();
    }
}
