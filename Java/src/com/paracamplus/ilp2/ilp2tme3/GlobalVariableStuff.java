package com.paracamplus.ilp2.ilp2tme3;

import java.io.Writer;
import java.math.BigDecimal;

import com.paracamplus.ilp1.compiler.Primitive;
import com.paracamplus.ilp1.interpreter.primitive.Newline;
import com.paracamplus.ilp1.interpreter.primitive.Print;

public class GlobalVariableStuff extends com.paracamplus.ilp1.interpreter.GlobalVariableStuff {
    /**
     * Method used by the interpreter
     */
    public static void fillGlobalVariables(com.paracamplus.ilp1.interpreter.interfaces.IGlobalVariableEnvironment env, Writer out) {
        env.addGlobalVariableValue("pi", new BigDecimal("3.1415926535"));
        env.addGlobalVariableValue(new Print(out));
        env.addGlobalVariableValue(new Newline(out));
        env.addGlobalVariableValue(new Sinus());
        env.addGlobalVariableValue(new MakeVector());
        env.addGlobalVariableValue(new VectorLength());
        env.addGlobalVariableValue(new VectorGet());
    }

    /**
     * Method used by the compiler
     */
    public static void fillGlobalVariables (com.paracamplus.ilp1.compiler.interfaces.IGlobalVariableEnvironment env) {
        env.addGlobalVariableValue("pi", "ILP_PI");
        env.addGlobalFunctionValue(new Primitive("print", "ILP_print", 1));
        env.addGlobalFunctionValue(new Primitive("newline", "ILP_newline", 0));
        env.addGlobalFunctionValue(new Primitive("throw", "ILP_throw", 1));
        env.addGlobalFunctionValue(new Primitive("sinus", "ILP_sinus", 1));
        env.addGlobalFunctionValue(new Primitive("makeVector", "ILP_makeVector", 1));
        env.addGlobalFunctionValue(new Primitive("vectorLength", "ILP_vectorLength", 1));
        env.addGlobalFunctionValue(new Primitive("vectorGet", "ILP_vectorGet", 1));
    }
}
