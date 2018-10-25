package com.paracamplus.ilp2.ilp2tme5.exceptions;

@SuppressWarnings("serial")
public class NamedContinueException extends ContinueException {
    private String name;

    public NamedContinueException(String name) {
        super();
        this.name = name;
    }

    public String getName() { return name; }
}
