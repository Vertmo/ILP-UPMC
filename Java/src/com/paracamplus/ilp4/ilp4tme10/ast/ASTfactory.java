package com.paracamplus.ilp4.ilp4tme10.ast;

import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTdefined;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTfactory;

public class ASTfactory extends com.paracamplus.ilp4.ast.ASTfactory implements IASTfactory {
    @Override
    public IASTexists newExists(IASTvariable var) {
        return new ASTexists(var);
    }

    @Override
    public IASTdefined newDefined(IASTvariable var) {
        return new ASTdefined(var);
    }
}
