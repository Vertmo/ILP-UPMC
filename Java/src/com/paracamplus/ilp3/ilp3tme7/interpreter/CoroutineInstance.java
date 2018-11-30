package com.paracamplus.ilp3.ilp3tme7.interpreter;

import java.util.concurrent.Semaphore;

import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interpreter.Function;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.interfaces.Invocable;

public class CoroutineInstance extends Thread {
    private Invocable function;
    private Object[] args;
    private Interpreter it;

    boolean isFinished;
    Semaphore sem1, sem2;

    public CoroutineInstance(Invocable function, Object[] args, Interpreter it) {
        this.function = function;
        this.args = args;
        this.it = it;
        isFinished = false;
        sem1 = new Semaphore(0);
        sem2 = new Semaphore(0);
    }

    @Override
    public void run() {
        try {
            sem1.acquireUninterruptibly();
            function.apply(it, args);
            isFinished = true;
            sem2.release();
        } catch(EvaluationException e) {
            System.err.println("Erreur dans la coroutine");
        }
    }

    public void yieldCoroutine() {
        sem2.release();
        sem1.acquireUninterruptibly();
    }

    public void resumeCoroutine() {
        if(isFinished) return;
        sem1.release();
        sem2.acquireUninterruptibly();
    }
}
