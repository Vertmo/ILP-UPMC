package com.paracamplus.ilp2.ilp2tme5.ast;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTbreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTcontinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTfactory;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedBreak;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedContinue;
import com.paracamplus.ilp2.ilp2tme5.interfaces.IASTnamedLoop;

public class ASTfactory extends com.paracamplus.ilp2.ast.ASTfactory implements IASTfactory {
    @Override
    public IASTbreak newBreak() {
        return new ASTbreak();
    }

    @Override
    public IASTcontinue newContinue() {
        return new ASTcontinue();
    }

    @Override
    public IASTnamedLoop newNamedLoop(IASTexpression condition, IASTexpression body, String name) {
        return new ASTnamedLoop(condition, body, name);
    }

    @Override
    public IASTnamedBreak newNamedBreak(String name) {
        return new ASTnamedBreak(name);
    }

    @Override
    public IASTnamedContinue newNamedContinue(String name) {
        return new ASTnamedContinue(name);
    }

}
