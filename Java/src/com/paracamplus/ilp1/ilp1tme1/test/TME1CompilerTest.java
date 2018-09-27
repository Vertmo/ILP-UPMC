package com.paracamplus.ilp1.ilp1tme1.test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.compiler.test.CompilerRunner;
import com.paracamplus.ilp1.compiler.test.CompilerTest;
import com.paracamplus.ilp1.parser.ParseException;

public class TME1CompilerTest extends CompilerTest {

    protected static String[] samplesDirName = { "SamplesTME1" };
    protected static String pattern = "ur?[0-78]\\d*-[123456](gfv)?";
    protected static String scriptCommand = "C/compileThenRun.sh +gc";
    protected static String XMLgrammarFile = "XMLGrammars/grammar1.rng";

    public TME1CompilerTest(final File file) {
        super(file);
    }

    @Parameters(name = "{0}")
    public static Collection<File[]> data() throws Exception {
        return CompilerRunner.getFileList(samplesDirName, pattern);
    }

    @Test
    public void processFile() throws CompilationException, ParseException, IOException {
        CompilerRunner run = new CompilerRunner();
        configureRunner(run);
        run.checkPrintingAndResult(file, run.compileAndRun(file));
    }
}
