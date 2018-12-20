package com.paracamplus.ilp4.ilp4tme10.compiler;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp4.ilp4tme10.ast.ASTdefined;
import com.paracamplus.ilp4.ilp4tme10.ast.ASTexists;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitor;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.normalizer.INormalizationFactory;

public class Normalizer extends com.paracamplus.ilp4.compiler.normalizer.Normalizer
    implements IASTvisitor<IASTexpression, INormalizationEnvironment, CompilationException> {

    public Normalizer(INormalizationFactory factory, IASTCclassDefinition objectClass) {
        super(factory, objectClass);
    }

    @Override
    public IASTexpression visit(IASTexists iast, INormalizationEnvironment data) throws CompilationException {
        return new ASTexists((IASTvariable) iast.getVariable().accept(this, data));
    }

    @Override
    public IASTexpression visit(IASTdefined iast, INormalizationEnvironment data) throws CompilationException {
        return new ASTdefined((IASTvariable) iast.getVariable().accept(this, data));
    }
}
