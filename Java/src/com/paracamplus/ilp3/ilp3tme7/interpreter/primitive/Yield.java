package com.paracamplus.ilp3.ilp3tme7.interpreter.primitive;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.Primitive;
import com.paracamplus.ilp3.ilp3tme7.interpreter.CoroutineInstance;

public class Yield extends Primitive {

    public Yield() {
        super("yield");
    }

    @Override
    public int getArity() {
        return 0;
    }

    @Override
    public Object apply() throws EvaluationException {
        Thread t = Thread.currentThread();
        if(t instanceof CoroutineInstance) {
            CoroutineInstance co = (CoroutineInstance) t;
            co.yieldCoroutine();
        } else {
            throw new EvaluationException("Ceci n'est pas une coroutine !");
        }
        return Boolean.FALSE;
    }
}
