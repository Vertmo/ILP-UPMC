package com.paracamplus.ilp2.ilp2tme5.exceptions;

@SuppressWarnings("serial")
public class NamedBreakException extends BreakException {
    private String name;

    public NamedBreakException(String name) {
        super();
        this.name = name;
    }

    public String getName() { return name; }
}
