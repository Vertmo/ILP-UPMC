package com.paracamplus.ilp2.ilp2tme5.interfaces;

import com.paracamplus.ilp1.interfaces.IASTexpression;

public interface IASTfactory extends com.paracamplus.ilp2.interfaces.IASTfactory {
    IASTbreak newBreak();
    IASTcontinue newContinue();
    IASTnamedLoop newNamedLoop(IASTexpression condition, IASTexpression body, String name);
    IASTnamedBreak newNamedBreak(String name);
    IASTnamedContinue newNamedContinue(String name);
}
