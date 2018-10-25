package com.paracamplus.ilp2.ilp2tme5;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.ILexicalEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.IOperatorEnvironment;
import com.paracamplus.ilp2.ilp2tme5.exceptions.BreakException;
import com.paracamplus.ilp2.ilp2tme5.exceptions.ContinueException;
import com.paracamplus.ilp2.ilp2tme5.exceptions.NamedBreakException;
import com.paracamplus.ilp2.ilp2tme5.exceptions.NamedContinueException;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedBreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedContinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedLoop;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTvisitor;
import com.paracamplus.ilp2.interfaces.IASTloop;

public class Interpreter extends com.paracamplus.ilp2.interpreter.Interpreter
    implements IASTvisitor<Object, ILexicalEnvironment, EvaluationException> {
    public Interpreter(IGlobalVariableEnvironment globalVariableEnvironment, IOperatorEnvironment operatorEnvironment) {
        super(globalVariableEnvironment, operatorEnvironment);
    }

    @Override
    public Object visit(IASTbreak iast, ILexicalEnvironment data) throws EvaluationException {
        throw new BreakException();
    }

    @Override
    public Object visit(IASTcontinue iast, ILexicalEnvironment data) throws EvaluationException {
        throw new ContinueException();
    }

    @Override
    public Object visit(IASTloop iast, ILexicalEnvironment lexenv) throws EvaluationException {
        while ( true ) {
            Object condition = iast.getCondition().accept(this, lexenv);
            if ( condition instanceof Boolean ) {
                Boolean c = (Boolean) condition;
                if ( ! c ) {
                    break;
                }
            }
            try {
                iast.getBody().accept(this, lexenv);
            } catch(NamedBreakException e) {
                throw e;
            } catch(NamedContinueException e) {
                throw e;
            } catch(BreakException e) {
                break;
            } catch(ContinueException e) {
                continue;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public Object visit(IASTnamedLoop iast, ILexicalEnvironment lexenv) throws EvaluationException {
        while ( true ) {
            Object condition = iast.getCondition().accept(this, lexenv);
            if ( condition instanceof Boolean ) {
                Boolean c = (Boolean) condition;
                if ( ! c ) {
                    break;
                }
            }
            try {
                iast.getBody().accept(this, lexenv);
            } catch(NamedBreakException e) {
                if(e.getName().equals(iast.getName())) break;
                else throw e;
            } catch(NamedContinueException e) {
                if(e.getName().equals(iast.getName())) continue;
                else throw e;
            } catch(BreakException e) {
                break;
            } catch(ContinueException e) {
                continue;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public Object visit(IASTnamedBreak iast, ILexicalEnvironment data) throws EvaluationException {
        throw new NamedBreakException(iast.getName());
    }

    @Override
    public Object visit(IASTnamedContinue iast, ILexicalEnvironment data) throws EvaluationException {
        throw new NamedContinueException(iast.getName());
    }
}
