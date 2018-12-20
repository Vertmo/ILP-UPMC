package com.paracamplus.ilp4.ilp4tme10.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interfaces.IASTvariable;
import com.paracamplus.ilp1.interfaces.IASTvisitor;
import com.paracamplus.ilp4.ilp4tme10.interfaces.IASTexists;

public class ASTexists implements IASTexists {
    protected IASTvariable var;
    private List<IASTCglobalVariable> globalVars;

    public ASTexists(IASTvariable var) {
        this.var = var;
        globalVars = new ArrayList<>();
    }

    @Override
    public<Result, Data, Anomaly extends Throwable> Result accept(IASTvisitor<Result, Data, Anomaly> visitor,
                                                                  Data data) throws Anomaly {
        return ((com.paracamplus.ilp4.ilp4tme10.interfaces.IASTvisitor<Result, Data, Anomaly>) visitor).visit(this, data);
    }

    @Override
    public IASTvariable getVariable() {
        return var;
    }

    @Override
    public void addGlobalVars(Collection<IASTCglobalVariable> globalVars) {
        this.globalVars.addAll(globalVars);
    }

    @Override
    public boolean exists() {
        for(IASTvariable v: globalVars) {
            if(v.getName().equals(var.getName())) return true;
        }
        return false;
    }
}
