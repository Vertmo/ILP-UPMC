package com.paracamplus.ilp1.ilp1tme1;

import com.paracamplus.ilp1.interpreter.test.InterpreterTest;

import java.io.File;

public class FileInterpreter {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Usage: java FileInterpreter <filename>");
            System.exit(1);
        }
        InterpreterTest it = new InterpreterTest(new File(args[0]));
        try {
            it.processFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
