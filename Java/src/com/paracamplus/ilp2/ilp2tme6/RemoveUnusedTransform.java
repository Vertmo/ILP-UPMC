package com.paracamplus.ilp2.ilp2tme6;

import java.util.ArrayList;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IAST;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTblock.IASTbinding;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interpreter.EmptyLexicalEnvironment;
import com.paracamplus.ilp1.interpreter.Function;
import com.paracamplus.ilp2.interfaces.IASTfactory;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTprogram;
import com.thaiopensource.relaxng.output.xsd.basic.Facet;

public class RemoveUnusedTransform extends CopyTransform<Void> {

    public RemoveUnusedTransform(IASTfactory factory) {
        super(factory);
    }

    @Override
    public IASTprogram visit(IASTprogram iast, Void data) throws CompilationException {
        UsedAnalysis ua = new UsedAnalysis();
        ArrayList<IASTfunctionDefinition> newDef = new ArrayList<>();
        // On commence par les functions globales
        for(IASTfunctionDefinition def: iast.getFunctionDefinitions()) {
            IASTvariable v = factory.newVariable(def.getName());
            boolean used = false;
            for(IASTfunctionDefinition def2: iast.getFunctionDefinitions()) {
                if(def != def2 && def2.getBody().accept(ua, v)) {
                    used = true;
                    break;
                }
            }
            if(used || iast.getBody().accept(ua, v)) {
                newDef.add(def);
            } else System.out.println("Supprimé la fonction " + def.getName());
        }
        return factory.newProgram(newDef.toArray(new IASTfunctionDefinition[newDef.size()]),
                                  // Puis les variables
                                  (IASTexpression) iast.getBody().accept(this, data));
    }

    @Override
    public IAST visit(IASTblock iast, Void data) throws CompilationException {
        UsedAnalysis ua = new UsedAnalysis();
        ArrayList<IASTbinding> newBindings = new ArrayList<>();
        for(IASTbinding b: iast.getBindings()) {
            if(iast.getBody().accept(ua, b.getVariable())) {
                newBindings.add(b);
            } else System.out.println("Supprimé la variable " + b.getVariable().getName());
        }
        return factory.newBlock(newBindings.toArray(new IASTbinding[newBindings.size()]),
                                (IASTexpression) iast.getBody().accept(this, data));
    }

}
