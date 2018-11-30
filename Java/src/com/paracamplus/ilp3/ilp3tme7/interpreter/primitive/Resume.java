package com.paracamplus.ilp3.ilp3tme7.interpreter.primitive;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.UnaryPrimitive;
import com.paracamplus.ilp3.ilp3tme7.interpreter.CoroutineInstance;

public class Resume extends UnaryPrimitive {

    public Resume() {
        super("resume");
    }

    @Override
    public Object apply (Object value) throws EvaluationException {
        if(value instanceof CoroutineInstance) {
            CoroutineInstance co = (CoroutineInstance) value;
            co.resumeCoroutine();
        } else {
            throw new EvaluationException("resume doit être appliqué à une coroutine");
        }
        return Boolean.FALSE;
    }
}
