package com.paracamplus.ilp2.ilp2tme6;

import java.util.Set;
import java.util.HashSet;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IASTalternative;
import com.paracamplus.ilp1.interfaces.IASTbinaryOperation;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTboolean;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTfloat;
import com.paracamplus.ilp1.interfaces.IASTinteger;
import com.paracamplus.ilp1.interfaces.IASTinvocation;
import com.paracamplus.ilp1.interfaces.IASTsequence;
import com.paracamplus.ilp1.interfaces.IASTstring;
import com.paracamplus.ilp1.interfaces.IASTunaryOperation;
import com.paracamplus.ilp2.interfaces.IASTassignment;
import com.paracamplus.ilp2.interfaces.IASTfunctionDefinition;
import com.paracamplus.ilp2.interfaces.IASTloop;
import com.paracamplus.ilp2.interfaces.IASTvisitor;

public class CallAnalysis implements IASTvisitor<Set<String>, Void, CompilationException> {
    private IASTfunctionDefinition[] definitions;

    public CallAnalysis(IASTfunctionDefinition[] definitions) {
        this.definitions = definitions;
    }

    private IASTexpression getBodyFromName(String n) throws CompilationException {
        for(IASTfunctionDefinition f: definitions) {
            if(f.getName().equals(n)) return f.getBody();
        }
        throw new CompilationException(n + " not in function definitions");
    }

    public boolean isRecursive(IASTvariable f) {
        Set<String> toVisit = new HashSet<String>();
        Set<String> visited = new HashSet<String>();
        try {
            toVisit.addAll(getBodyFromName(f.getName()).accept(this, null));
        } catch(CompilationException e) {}
        while(!toVisit.isEmpty() && !visited.contains(f.getName())) {
            try {
                Set<String> found = getBodyFromName((String) toVisit.toArray()[0]).accept(this, null);
                visited.add((String) toVisit.toArray()[0]);
                toVisit.addAll(found);
                toVisit.removeAll(visited);
            } catch(CompilationException e) {}
        }
        return visited.contains(f.getName());
    }

    @Override
    public Set<String> visit(IASTalternative iast, Void data) throws CompilationException {
        Set<String> list = new HashSet<String>();
        list.addAll(iast.getCondition().accept(this, data));
        list.addAll(iast.getConsequence().accept(this, data));
        if(iast.isTernary()) list.addAll(iast.getAlternant().accept(this, data));
        return list;
    }

    @Override
    public Set<String> visit(IASTbinaryOperation iast, Void data) throws CompilationException {
        Set<String> list = new HashSet<String>();
        list.addAll(iast.getLeftOperand().accept(this, data));
        list.addAll(iast.getRightOperand().accept(this, data));
        return list;
    }

    @Override
    public Set<String> visit(IASTblock iast, Void data) throws CompilationException {
        Set<String> list = new HashSet<String>();
        for(IASTblock.IASTbinding b : iast.getBindings()) {
            list.addAll(b.getInitialisation().accept(this, data));
        }
        list.addAll(iast.getBody().accept(this, data));
        return list;
    }

    @Override
    public Set<String> visit(IASTboolean iast, Void data) throws CompilationException {
        return new HashSet<String>();
    }

    @Override
    public Set<String> visit(IASTfloat iast, Void data) throws CompilationException {
        return new HashSet<String>();
    }

    @Override
    public Set<String> visit(IASTinteger iast, Void data) throws CompilationException {
        return new HashSet<String>();
    }

    @Override
    public Set<String> visit(IASTinvocation iast, Void data) throws CompilationException {
        Set<String> list = new HashSet<String>();
        if(iast.getFunction() instanceof IASTvariable) {
            try {
                getBodyFromName(((IASTvariable) iast.getFunction()).getName());
                list.add(((IASTvariable) iast.getFunction()).getName());
            } catch(CompilationException e) {}
        }
        for(IASTexpression a: iast.getArguments()) {
            list.addAll(a.accept(this, data));
        }
        return list;
    }

    @Override
    public Set<String> visit(IASTsequence iast, Void data) throws CompilationException {
        Set<String> list = new HashSet<String>();
        for(IASTexpression e: iast.getExpressions()) {
            list.addAll(e.accept(this, data));
        }
        return list;
    }

    @Override
    public Set<String> visit(IASTstring iast, Void data) throws CompilationException {
        return new HashSet<String>();
    }

    @Override
    public Set<String> visit(IASTunaryOperation iast, Void data) throws CompilationException {
        return iast.getOperand().accept(this, data);
    }

    @Override
    public Set<String> visit(IASTvariable iast, Void data) throws CompilationException {
        return new HashSet<String>();
    }

    @Override
    public Set<String> visit(IASTassignment iast, Void data) throws CompilationException {
        return iast.getExpression().accept(this, data);
    }

    @Override
    public Set<String> visit(IASTloop iast, Void data) throws CompilationException {
        Set<String> list = new HashSet<String>();
        list.addAll(iast.getCondition().accept(this, data));
        list.addAll(iast.getBody().accept(this, data));
        return list;
    }
}
