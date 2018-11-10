package com.paracamplus.ilp2.ilp2tme6;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.normalizer.INormalizationEnvironment;
import com.paracamplus.ilp1.compiler.normalizer.NoSuchLocalVariableException;
import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp2.interfaces.IASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTprogram;

public class RenameTransform extends CopyTransform<INormalizationEnvironment> {
    private int nbRenamed;

    public RenameTransform(IASTfactory factory) {
        super(factory);
        nbRenamed = 0;
    }

    @Override
    public IASTprogram visit(IASTprogram iast, INormalizationEnvironment data) throws CompilationException {
        IASTfunctionDefinition[] definitions = new IASTfunctionDefinition[iast.getFunctionDefinitions().length];
        for(int i = 0; i < iast.getFunctionDefinitions().length; i++) {
            IASTfunctionDefinition def = iast.getFunctionDefinitions()[i];
            IASTvariable vars[] = new IASTvariable[def.getVariables().length];
            for(int j = 0; j < def.getVariables().length; j++) {
                IASTvariable v = def.getVariables()[j];
                data = data.extend(v, factory.newVariable(v.getName()+"_"+(++nbRenamed)));
                vars[j] = (IASTvariable) v.accept(this, data);
            }
            definitions[i] = factory.newFunctionDefinition(def.getFunctionVariable(),
                                                           vars,
                                                           (IASTexpression) def.getBody().accept(this, data));
        }
        return factory.newProgram(definitions, (IASTexpression) iast.getBody().accept(this, data));
    }

    @Override
    public IAST visit(IASTblock iast, INormalizationEnvironment data) throws CompilationException {
        IASTbinding[] bindings = new IASTbinding[iast.getBindings().length];
        INormalizationEnvironment oldData = data;
        for(int i = 0; i < iast.getBindings().length; i++) {
            IASTbinding bin = iast.getBindings()[i];
            data = data.extend(bin.getVariable(), factory.newVariable(bin.getVariable().getName()+"_"+(++nbRenamed)));
            bindings[i] = factory.newBinding((IASTvariable) bin.getVariable().accept(this, data),
                                             (IASTexpression) bin.getInitialisation().accept(this, oldData));
        }
        return factory.newBlock(bindings,
                                (IASTexpression) iast.getBody().accept(this, data));
    }

    @Override
    public IAST visit(IASTvariable iast, INormalizationEnvironment data) throws CompilationException {
        try {
            return data.renaming(iast);
        } catch(NoSuchLocalVariableException e) {
            return iast;
        }
    }

}
