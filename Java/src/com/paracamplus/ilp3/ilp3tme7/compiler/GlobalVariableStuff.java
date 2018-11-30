package com.paracamplus.ilp3.ilp3tme7.compiler;

import com.paracamplus.ilp1.compiler.Primitive;
import com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment;

public class GlobalVariableStuff {
    public static void fillGlobalVariables (IGlobalVariableEnvironment env) {
        env.addGlobalVariableValue("pi", "ILP_PI");
        env.addGlobalFunctionValue(new Primitive("print", "ILP_print", 1));
        env.addGlobalFunctionValue(new Primitive("newline", "ILP_newline", 0));
        env.addGlobalFunctionValue(new Primitive("throw", "ILP_throw", 1));
        env.addGlobalFunctionValue(new Primitive("yield", "ILP_yield", 0));
        env.addGlobalFunctionValue(new Primitive("resume", "ILP_resume", 1));
    }
}
