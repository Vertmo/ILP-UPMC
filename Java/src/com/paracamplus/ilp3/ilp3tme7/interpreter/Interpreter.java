package com.paracamplus.ilp3.ilp3tme7.interpreter;

import java.util.List;
import java.util.Vector;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.Invocable;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTcostart;
import com.paracamplus.ilp3.ilp3tme7.interfaces.IASTvisitor;

public class Interpreter extends com.paracamplus.ilp3.interpreter.Interpreter
    implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {
    public Interpreter(IGlobalVariableEnvironment globalVariableEnvironment, IOperatorEnvironment operatorEnvironment) {
        super(globalVariableEnvironment, operatorEnvironment);
	}

    @Override
    public Object visit(IASTcostart iast, ILexicalEnvironment data) throws EvaluationException {
        Object function = iast.getFunction().accept(this, data);
        if (function instanceof Invocable) {
            Invocable f = (Invocable) function;
            List<Object> args = new Vector<Object>();
            for(IASTexpression arg : iast.getArguments() ) {
                Object value = arg.accept(this, data);
                args.add(value);
            }
            CoroutineInstance co = new CoroutineInstance(f, args.toArray(), this);
            co.start();
            return co;
        } else {
            String msg = "Cannot apply " + function;
            throw new EvaluationException(msg);
        }
    }

}
