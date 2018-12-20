package com.paracamplus.ilp4.ilp4tme10.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.interfaces.IASTCglobalVariable;
import com.paracamplus.ilp1.interpreter.GlobalVariableEnvironment;
import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp4.compiler.interfaces.IASTCclassDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCmethodDefinition;
import com.paracamplus.ilp4.compiler.interfaces.IASTCprogram;
import com.paracamplus.ilp4.compiler.normalizer.INormalizationFactory;
import com.paracamplus.ilp4.compiler.normalizer.NormalizationFactory;
import com.paracamplus.ilp4.compiler.test.CompilerRunner;
import com.paracamplus.ilp4.ilp4tme10.compiler.GlobalVariableCollector;
import com.paracamplus.ilp4.ilp4tme10.compiler.Normalizer;
import com.paracamplus.ilp4.ilp4tme10.interpreter.GlobalVariableAdder;
import com.paracamplus.ilp4.interfaces.IASTprogram;

public class InterpreterRunner extends com.paracamplus.ilp1.interpreter.test.InterpreterRunner {
    protected IASTCclassDefinition createObjectClass() {
        IASTCclassDefinition oc = new IASTCclassDefinition() {
                @Override
                public String getName() {
                    return "Object";
                }
                @Override
                public IASTCclassDefinition getSuperClass() {
                    throw new RuntimeException("No super class for Object");
                }
                @Override
                public String[] getProperFieldNames() {
                    return new String[0];
                }
                @Override
                public String[] getTotalFieldNames() {
                    return getProperFieldNames();
                }
                @Override
                public int getFieldOffset(String fieldName)
                    throws CompilationException {
                    String msg = "No such field " + fieldName;
                    throw new CompilationException(msg);
                }
                @Override
                public IASTCmethodDefinition[] getProperMethodDefinitions() {
                    return predefinedMethods;
                }
                @Override
                public IASTCmethodDefinition[] getNewProperMethodDefinitions() {
                    return predefinedMethods;
                }
                protected IASTCmethodDefinition[] predefinedMethods =
                    new IASTCmethodDefinition[2];
                @Override
                public IASTCmethodDefinition[] getTotalMethodDefinitions() {
                    return getProperMethodDefinitions();
                }
            };
        IASTCmethodDefinition mdprint = new CompilerRunner.ASTCprimitiveMethodDefinition(
                                                                          "print", "ILPm_print", oc);
        oc.getProperMethodDefinitions()[0] = mdprint;
        IASTCmethodDefinition mdclassOf = new CompilerRunner.ASTCprimitiveMethodDefinition(
                                                                            "classOf", "ILPm_classOf", oc);
        oc.getProperMethodDefinitions()[1] = mdclassOf;
        return oc;
    }

    @Override
    public void testFile(File file) throws ParseException, IOException, EvaluationException {
        System.err.println("Testing " + file.getAbsolutePath() + " ...");
        assertTrue(file.exists());
        IASTprogram program = (IASTprogram) parser.parse(file);

        INormalizationFactory nf = new NormalizationFactory();
        Normalizer normalizer = new Normalizer(nf, createObjectClass());
        try {
            IASTCprogram newprogram = normalizer.transform(program);
            Set<IASTCglobalVariable> globalVars = new GlobalVariableCollector().analyze(newprogram);
            new GlobalVariableAdder().visit(program, globalVars);
        } catch(CompilationException e) { throw new EvaluationException(e.getMessage()); }

        // TODO global variables
        interpretProgram(program);
    }
}
