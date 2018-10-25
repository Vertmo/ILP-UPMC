package com.paracamplus.ilp2.ilp2tme5.exceptions;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;

@SuppressWarnings("serial")
public class ContinueException extends EvaluationException {
    public ContinueException() {
        super("continue should be inside a loop");
    }
}
