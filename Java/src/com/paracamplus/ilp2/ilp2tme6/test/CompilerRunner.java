package com.paracamplus.ilp2.ilp2tme6.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import com.paracamplus.ilp1.compiler.CompilationException;
import com.paracamplus.ilp1.interfaces.IASTprogram;
import com.paracamplus.ilp1.parser.ParseException;
import com.paracamplus.ilp1.tools.FileTool;
import com.paracamplus.ilp1.tools.ProgramCaller;
import com.paracamplus.ilp2.ast.ASTfactory;
import com.paracamplus.ilp2.ilp2tme6.InlineTransform;
import com.paracamplus.ilp2.ilp2tme6.RemoveUnusedTransform;

public class CompilerRunner extends com.paracamplus.ilp1.compiler.test.CompilerRunner {
    @Override
    public String compileAndRun(File file) throws ParseException, CompilationException, IOException {
        System.err.println("Testing " + file.getAbsolutePath() + " ...");
        assertTrue(file.exists());

        // lancement du parsing
        IASTprogram program = parser.parse(file);

        // Optimisation du TME6
        com.paracamplus.ilp2.interfaces.IASTprogram program2 = (com.paracamplus.ilp2.interfaces.IASTprogram) program;
        InlineTransform itr = new InlineTransform(new ASTfactory(), program2.getFunctionDefinitions());
        program2 = itr.visit(program2, null);

        // Bonus: suppression des variables et fonctions inutilisées
        RemoveUnusedTransform rut = new RemoveUnusedTransform(new ASTfactory());
        program2 = rut.visit(program2, null);

        // lancement de la compilation vers C
        if (compiler == null) {
        	throw new CompilationException("no compiler set");
        }
        String compiled = compiler.compile(program2);
        File cFile = FileTool.changeSuffix(file, "c");
        FileTool.stuffFile(cFile, compiled);

        // pretty-print du C généré et affichage
        try {
            String indentProgram = "indent " + cFile.getAbsolutePath();
            ProgramCaller pcindent = new ProgramCaller(indentProgram);
            pcindent.run();
        } catch (Exception exc) {
        }
        System.out.println(FileTool.slurpFile(cFile));

        // lancement du script de compilation et d'exécution
        if (runtimeScript == null) {
        	throw new CompilationException("runtime script not set");
        }
       String compileProgram = "bash " + runtimeScript + " " + cFile.getAbsolutePath();
        ProgramCaller pc = new ProgramCaller(compileProgram);
        pc.setVerbose();
        pc.run();
        assertEquals("Comparing return code", 0, pc.getExitValue());
        return pc.getStdout().trim();
    }
}
