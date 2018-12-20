package com.paracamplus.ilp4.ilp4tme10.interfaces;

import java.util.Collection;

import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

public interface IASTexists extends IASTexpression {
    public IASTvariable getVariable();
    public void addGlobalVars(Collection<IASTCglobalVariable> globalVars);
    public boolean exists();
}
