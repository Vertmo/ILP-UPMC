package com.paracamplus.ilp4.ilp4tme8.interpreter;

import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;

import java.util.List;
import java.util.Vector;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASThasProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTreadProperty;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme8.interfaces.IASTwriteProperty;
import com.paracamplus.ilp4.interfaces.IASTinstantiation;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;
import com.paracamplus.ilp4.interpreter.interfaces.IClassEnvironment;

public class Interpreter extends com.paracamplus.ilp4.interpreter.Interpreter
    implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {

    public Interpreter (IGlobalVariableEnvironment globalVariableEnvironment,
                        IOperatorEnvironment operatorEnvironment,
                        IClassEnvironment classEnvironment ) {
        super(globalVariableEnvironment, operatorEnvironment, classEnvironment);
        this.classEnvironment = classEnvironment;
    }

    @Override
    public Object visit(IASTinstantiation iast, ILexicalEnvironment lexenv)
        throws EvaluationException {
        IClass clazz = getClassEnvironment().getILP9Class(iast.getClassName());
        List<Object> args = new Vector<Object>();
        for ( IASTexpression arg : iast.getArguments() ) {
            Object value = arg.accept(this, lexenv);
            args.add(value);
        }
        return new ILP9Instance(clazz, args.toArray());
    }


    @Override
    public Object visit(IASTreadProperty iast, ILexicalEnvironment data) throws EvaluationException {
        Object target = iast.getTarget().accept(this, data);
        if ( target instanceof ILP9Instance ) {
            return ((ILP9Instance) target).read((String) iast.getFieldExpr().accept(this, data));
        } else {
            String msg = "Not an ILP9 instance " + target;
            throw new EvaluationException(msg);
        }
    }

    @Override
    public Object visit(IASTwriteProperty iast, ILexicalEnvironment data) throws EvaluationException {
        Object target = iast.getTarget().accept(this, data);
        if ( target instanceof ILP9Instance ) {
            return ((ILP9Instance) target).write((String) iast.getFieldExpr().accept(this, data),
                                                 iast.getValue().accept(this, data));
        } else {
            String msg = "Not an ILP9 instance " + target;
            throw new EvaluationException(msg);
        }
    }

    @Override
    public Object visit(IASThasProperty iast, ILexicalEnvironment data) throws EvaluationException {
        Object target = iast.getTarget().accept(this, data);
        if ( target instanceof ILP9Instance ) {
            String[] fields = ((ILP9Instance) target).classOf().getProperFieldNames();
            String fieldName = (String) iast.getFieldExpr().accept(this, data);
            for(String s: fields) {
                if(s.equals(fieldName)) return true;
            }
            return false;
        } else {
            String msg = "Not an ILP9 instance " + target;
            throw new EvaluationException(msg);
        }
    }
}
