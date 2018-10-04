package com.paracamplus.ilp1.ilp1tme2.ex2;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.paracamplus.ilp1.interfaces.IASTfactory;
import com.paracamplus.ilp1.interfaces.IASTblock;
import com.paracamplus.ilp1.interfaces.IASTexpression;
import com.paracamplus.ilp1.interfaces.IASTvariable;

import antlr4.ILPMLgrammar1Listener;
import static antlr4.ILPMLgrammar1Parser.*;

public class ILPMLListener extends com.paracamplus.ilp1.parser.ilpml.ILPMLListener implements ILPMLgrammar1Listener {
    private int constCount;

    public ILPMLListener(IASTfactory factory) {
        super(factory);
        constCount = 0;
    }

    public int getConstCount() {
        return constCount;
    }

    @Override
    public void exitConstFloat(
                               ConstFloatContext ctx) {
        ctx.node = factory.newFloatConstant(ctx.floatConst.getText());
        constCount++;
    }

    @Override
    public void	exitConstInteger(
                                 ConstIntegerContext ctx) {
        ctx.node = factory.newIntegerConstant(ctx.intConst.getText());
        constCount++;
    }

    @Override
    public void exitConstFalse(
                               ConstFalseContext ctx) {
        ctx.node = factory.newBooleanConstant("false");
        constCount++;
    }

    @Override
    public void exitConstTrue(
                              ConstTrueContext ctx) {
        ctx.node = factory.newBooleanConstant("true");
        constCount++;
    }

    @Override
    public void exitConstString(
                                ConstStringContext ctx) {
        /*
         * On enlève le " initial et final, et on interprète les codes
         * d'échapement \n, \r, \t, \"
         */
        String s = ctx.getText();
        StringBuilder buf = new StringBuilder();
        for (int i = 1; i < s.length() - 1; i++) {
            if (s.charAt(i) == '\\' && i < s.length() - 2) {
                switch (s.charAt(i+1)) {
                case 'n': buf.append('\n'); i++; break;
                case 'r': buf.append('\r'); i++; break;
                case 't': buf.append('\t'); i++; break;
                case '"': buf.append('\"'); i++; break;
                default: buf.append(s.charAt(i));
                }
            }
            else buf.append(s.charAt(i));
        }
        ctx.node = factory.newStringConstant(buf.toString());
        constCount++;
    }

}
