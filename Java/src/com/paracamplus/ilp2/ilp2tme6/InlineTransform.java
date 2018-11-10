package com.paracamplus.ilp2.ilp2tme6;

import com.paracamplus.ilp1.ast.ASTblock;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.NormalizationEnvironment;
import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.interfaces.IASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTprogram;

public class InlineTransform extends CopyTransform<IASTfunctionDefinition> {
    private CallAnalysis ca;

    public InlineTransform(IASTfactory factory, IASTfunctionDefinition[] definitions) {
        super(factory);
        ca = new CallAnalysis(definitions);
    }

    @Override
    public IASTprogram visit(IASTprogram iast, IASTfunctionDefinition data) throws CompilationException {
        iast = new RenameTransform(factory).visit(iast, NormalizationEnvironment.EMPTY);
        for(IASTfunctionDefinition def: iast.getFunctionDefinitions()) {
            if(!ca.isRecursive(def.getFunctionVariable())) {
                IASTfunctionDefinition[] newDefs = new IASTfunctionDefinition[iast.getFunctionDefinitions().length];
                for(int i=0; i<iast.getFunctionDefinitions().length; i++) {
                    IASTfunctionDefinition d = iast.getFunctionDefinitions()[i];
                    newDefs[i] = factory.newFunctionDefinition(d.getFunctionVariable(),
                                                               d.getVariables(),
                                                               (IASTexpression) d.getBody().accept(this, def));
                }
                iast = factory.newProgram(newDefs,
                                          (IASTexpression) iast.getBody().accept(this, def));
            }
        }
        return iast;
    }

    @Override
    public IAST visit(IASTinvocation iast, IASTfunctionDefinition data) throws CompilationException {
        if(iast.getFunction() instanceof IASTvariable) {
            IASTvariable v = (IASTvariable) iast.getFunction();
            if(v.getName().equals(data.getName())) {
                // On fait le vrai travail ici
                IASTblock.IASTbinding[] bindings = new ASTblock.ASTbinding[data.getVariables().length];
                for(int i = 0; i < data.getVariables().length; i++) {
                    bindings[i] = factory.newBinding(data.getVariables()[i],
                                                     (IASTexpression) iast.getArguments()[i].accept(this, data));
                }
                return factory.newBlock(bindings, (IASTexpression) data.getBody().accept(this, data));
            }
        }
        return super.visit(iast, data);
    }
}
