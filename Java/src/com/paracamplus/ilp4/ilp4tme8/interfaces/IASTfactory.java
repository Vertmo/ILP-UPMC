package com.paracamplus.ilp4.ilp4tme8.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

public interface IASTfactory extends com.paracamplus.ilp4.interfaces.IASTfactory {
    public IASTexpression newReadProperty(IASTexpression fieldExpr, IASTexpression object);
    public IASTexpression newWriteProperty(IASTexpression fieldExpr, IASTexpression object, IASTexpression value);
    public IASTexpression newHasProperty(IASTexpression fieldExpr, IASTexpression object);
}
