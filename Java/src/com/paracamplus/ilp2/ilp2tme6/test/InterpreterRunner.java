package com.paracamplus.ilp2.ilp2tme6.test;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.interpreter.EmptyLexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp2.ast.ASTfactory;
import com.paracamplus.ilp2.ilp2tme6.InlineTransform;

public class InterpreterRunner extends com.paracamplus.ilp1.interpreter.test.InterpreterRunner {
    @Override
    public void interpretProgram(IASTprogram program) throws EvaluationException {
        if (interpreter == null) {
            throw new EvaluationException("interpreter not set");
        }

        // Optimisation du TME6
        com.paracamplus.ilp2.interfaces.IASTprogram program2 = (com.paracamplus.ilp2.interfaces.IASTprogram) program;
        try {
            InlineTransform itr = new InlineTransform(new ASTfactory(), program2.getFunctionDefinitions());
            program2 = itr.visit(program2, null);
        } catch (CompilationException e) {
            throw new EvaluationException("CopyTransform: "+e.getMessage());
        }

        ILexicalEnvironment lexenv = new EmptyLexicalEnvironment();
        result = interpreter.visit(program2, lexenv);
        printing = stdout.toString();
        System.out.println("  Value: " + result);
        if ( ! "".equals(printing) ) {
            System.out.println("  Printing: " + printing);
        }
    }
}
