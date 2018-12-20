package com.paracamplus.ilp4.ilp4tme10.interpreter;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp4.interpreter.interfaces.IClassEnvironment;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitor;

public class Interpreter extends com.paracamplus.ilp4.interpreter.Interpreter
    implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {

    public Interpreter(IGlobalVariableEnvironment globalVariableEnvironment, IOperatorEnvironment operatorEnvironment,
                       IClassEnvironment classEnvironment) {
        super(globalVariableEnvironment, operatorEnvironment, classEnvironment);
    }

    @Override
    public Object visit(IASTexists iast, ILexicalEnvironment data) throws EvaluationException {
        return iast.exists() || data.isPresent(iast.getVariable()) ||
            (getGlobalVariableEnvironment().getGlobalVariableValue(iast.getVariable().getName()) != null);
    }

    @Override
    public Object visit(IASTdefined iast, ILexicalEnvironment data) throws EvaluationException {
        return data.isPresent(iast.getVariable()) ||
            (getGlobalVariableEnvironment().getGlobalVariableValue(iast.getVariable().getName()) != null);
    }
}
