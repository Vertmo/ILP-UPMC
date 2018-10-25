package com.paracamplus.ilp2.ilp2tme5.exceptions;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

@SuppressWarnings("serial")
public class BreakException extends EvaluationException {
    public BreakException() {
        super("break should be inside a loop");
    }
}
