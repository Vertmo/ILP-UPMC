package com.paracamplus.ilp4.ilp4tme8.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import com.paracamplus.ilp1.compiler.AssignDestination;
import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.NoDestination;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.compiler.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp4.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTCvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;
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
    public Void visit(IASTreadProperty iast, Context data) throws CompilationException {
        emit("{\n");
        IASTvariable tmpInstance = data.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        Context c = data.redirect(new AssignDestination(tmpInstance));
        iast.getTarget().accept(this, c);

        IASTvariable tmpFieldVar = data.newTemporaryVariable();
        emit(" ILP_Object " + tmpFieldVar.getMangledName() + "; \n");
        Context c2 = data.redirect(new AssignDestination(tmpFieldVar));
        iast.getFieldExpr().accept(this, c2);

        emit(data.destination.compile());
        emit("ILP_read_property(" + tmpInstance.getMangledName() + "," + tmpFieldVar.getMangledName() + ");");
        emit("}\n");
        return null;
    }

    @Override
    public Void visit(IASTwriteProperty iast, Context data) throws CompilationException {
        emit("{\n");
        IASTvariable tmpInstance = data.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        Context c = data.redirect(new AssignDestination(tmpInstance));
        iast.getTarget().accept(this, c);

        IASTvariable tmpFieldVar = data.newTemporaryVariable();
        emit(" ILP_Object " + tmpFieldVar.getMangledName() + "; \n");
        Context c2 = data.redirect(new AssignDestination(tmpFieldVar));
        iast.getFieldExpr().accept(this, c2);

        IASTvariable tmpValVar = data.newTemporaryVariable();
        emit(" ILP_Object " + tmpValVar.getMangledName() + "; \n");
        Context c3 = data.redirect(new AssignDestination(tmpValVar));
        iast.getValue().accept(this, c3);

        emit(data.destination.compile());
        emit("ILP_write_property(" + tmpInstance.getMangledName() + "," + tmpFieldVar.getMangledName() +
             "," + tmpValVar.getMangledName() + ");");
        emit("}\n");
        return null;

    }

    @Override
    public Void visit(IASThasProperty iast, Context data) throws CompilationException {
        emit("{\n");
        IASTvariable tmpInstance = data.newTemporaryVariable();
        emit("  ILP_Object " + tmpInstance.getMangledName() + "; \n");
        Context c = data.redirect(new AssignDestination(tmpInstance));
        iast.getTarget().accept(this, c);

        IASTvariable tmpFieldVar = data.newTemporaryVariable();
        emit(" ILP_Object " + tmpFieldVar.getMangledName() + "; \n");
        Context c2 = data.redirect(new AssignDestination(tmpFieldVar));
        iast.getFieldExpr().accept(this, c2);

        emit(data.destination.compile());
        emit("ILP_has_property(" + tmpInstance.getMangledName() + "," + tmpFieldVar.getMangledName() + ");");
        emit("}\n");
        return null;
    }
}
