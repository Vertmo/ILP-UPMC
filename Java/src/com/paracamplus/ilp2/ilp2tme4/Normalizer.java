package com.paracamplus.ilp2.ilp2tme4;

import com.paracamplus.ilp1.ast.ASToperator;
import com.paracamplus.ilp1.ast.ASTunaryOperation;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTunless;
import com.paracamplus.ilp2.ilp2tme4.interfaces.IASTvisitor;

import sun.text.CompactByteArray;

public class Normalizer extends com.paracamplus.ilp2.compiler.normalizer.Normalizer
    implements IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {
    public Normalizer (INormalizationFactory factory) {
        super(factory);
    }

    @Override
    public IASTexpression visit(IASTunless iast, INormalizationEnvironment data) throws CompilationException {
        return new ASTunless(iast.getCondition().accept(this, data),
                             iast.getBody().accept(this, data));
    }

}
