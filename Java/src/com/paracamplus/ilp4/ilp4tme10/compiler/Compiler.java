package com.paracamplus.ilp4.ilp4tme10.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTClocalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp4.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
import com.paracamplus.ilp4.interfaces.IASTprogram;

public class Compiler extends com.paracamplus.ilp4.compiler.Compiler
    implements IASTCvisitor<Void, Compiler.Context, CompilationException> {

    public Compiler(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
        super(ioe, igve);
    }

    public IASTCprogram normalize(IASTprogram program,
                                  IASTCclassDefinition objectClass)
        throws CompilationException {
        INormalizationFactory nf = new NormalizationFactory();
        Normalizer normalizer = new Normalizer(nf, objectClass);
        IASTCprogram newprogram = normalizer.transform(program);
        return newprogram;
    }

    public String compile(IASTprogram program,
                          IASTCclassDefinition objectClass)
        throws CompilationException {

        IASTCprogram newprogram = normalize(program, objectClass);
        newprogram = (IASTCprogram) optimizer.transform(newprogram);

        GlobalVariableCollector gvc = new GlobalVariableCollector();
        Set<IASTCglobalVariable> gvs = gvc.analyze(newprogram);
        newprogram.setGlobalVariables(gvs);

        // Adding global variables
        new GlobalVariableAdder().visit(newprogram, gvs);

        FreeVariableCollector fvc = new FreeVariableCollector(newprogram);
        newprogram = fvc.analyze();

        Context context = new Context(NoDestination.NO_DESTINATION);
        StringWriter sw = new StringWriter();
        try {
            out = new BufferedWriter(sw);
            visit(newprogram, context);
            out.flush();
        } catch (IOException exc) {
            throw new CompilationException(exc);
        }
        return sw.toString();
    }

    @Override
    public Void visit(IASTexists iast, Context context) throws CompilationException {
        emit(context.destination.compile());
        if(iast.getVariable() instanceof IASTClocalVariable) emit("ILP_TRUE;\n");
        else if(iast.exists() || globalVariableEnvironment.contains(iast.getVariable())) emit("ILP_TRUE;\n");
        else emit("ILP_FALSE;\n");
        return null;
    }

    @Override
    public Void visit(IASTdefined iast, Context context) throws CompilationException {
        if(iast.getVariable() instanceof IASTClocalVariable) {
            emit(context.destination.compile());
            emit("ILP_TRUE;\n");
        } else if(globalVariableEnvironment.contains(iast.getVariable())) {
            emit(context.destination.compile());
            emit("ILP_TRUE;\n");
        } else if(iast.exists()) {
            IASTvariable tmp = context.newTemporaryVariable();
            emit("ILP_Object " + tmp.getMangledName() + ";\n");
            emit("if (");
            emit(iast.getVariable().getMangledName() + " == NULL");
            emit(") {\n");
            emit(tmp.getMangledName() + " = ILP_FALSE;\n");
            emit("} else {\n");
            emit(tmp.getMangledName() + " = ILP_TRUE;\n");
            emit("}\n");
            emit(context.destination.compile());
            emit(tmp.getMangledName());
            emit(";\n");
        }
        else {
            emit(context.destination.compile());
            emit("ILP_FALSE;\n");
        }
        return null;
    }

}
