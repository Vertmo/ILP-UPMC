package com.paracamplus.ilp3.ilp3tme7.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import com.paracamplus.ilp3.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp1.compiler.ReturnDestination;
import com.paracamplus.ilp1.compiler.VoidDestination;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCglobalCostart;
import com.paracamplus.ilp3.ilp3tme7.compiler.interfaces.IASTCvisitor;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTcostart;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalFunctionVariable;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalInvocation;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp3.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp3.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp3.ilp3tme7.compiler.Normalizer;
import com.paracamplus.ilp3.interfaces.IASTprogram;

public class Compiler extends com.paracamplus.ilp3.compiler.Compiler
    implements IASTCvisitor<Void, Compiler.Context, CompilationException> {

    public Compiler(IOperatorEnvironment ioe, IGlobalVariableEnvironment igve) {
        super(ioe, igve);
    }


    public IASTCprogram normalize(IASTprogram program)
        throws CompilationException {
        INormalizationFactory nf = new NormalizationFactory();
        Normalizer normalizer = new Normalizer(nf);
        IASTCprogram newprogram = normalizer.transform(program);
        return newprogram;
    }

    @Override
    public String compile(IASTprogram program)
        throws CompilationException {

        IASTCprogram newprogram = normalize(program);
        newprogram = (IASTCprogram) optimizer.transform(newprogram);

        GlobalVariableCollector gvc = new GlobalVariableCollector();
        Set<IASTCglobalVariable> gvs = gvc.analyze(newprogram);
        newprogram.setGlobalVariables(gvs);

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
    public Void visit(IASTcostart iast, Context data) throws CompilationException {
        if(iast instanceof IASTCglobalCostart) {
            return visit((IASTCglobalCostart) iast, data);
        } else {
            throw new RuntimeException("Should not occur");
        }
    }

    @Override
    public Void visit(IASTCglobalCostart iast, Context context) throws CompilationException {
        IASTexpression[] arguments = iast.getArguments();
        if(iast.getFunction() instanceof IASTCglobalVariable) {
            emit("{\n");
            IASTvariable closure = context.newTemporaryVariable();
            emit(" ILP_Object " + closure.getMangledName() + " = ILP_make_closure(");
            emit("ilp__"+((IASTCglobalVariable) iast.getFunction()).getMangledName());
            emit(", " + arguments.length + ", 0);\n");
            IASTvariable[] tmps = new IASTvariable[arguments.length];
            for ( int i=0 ; i<arguments.length ; i++ ) {
                IASTvariable tmp = context.newTemporaryVariable();
                emit("  ILP_Object " + tmp.getMangledName() + "; \n");
                tmps[i] = tmp;
            }
            for ( int i=0 ; i<arguments.length ; i++ ) {
                IASTexpression expression = arguments[i];
                IASTvariable tmp = tmps[i];
                Context c = context.redirect(new AssignDestination(tmp));
                expression.accept(this, c);
            }
            emit(context.destination.compile());
            emit("ILP_costart(");
            emit(closure.getMangledName());
            emit(", ");
            emit(arguments.length);
            for ( int i=0 ; i<arguments.length ; i++ ) {
                IASTvariable tmp = (IASTvariable) tmps[i];
                emit(", ");
                emit(tmp.getMangledName());
            }
            emit(");\n}\n");
        } else {
            throw new RuntimeException("Should not occur");
        }
            return null;
    }

}
