package com.paracamplus.ilp2.ilp2tme3;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.UnaryPrimitive;

public class Sinus extends UnaryPrimitive {
    public Sinus() {
        super("sinus");
    }

	@Override
	public Object apply(Object d) throws EvaluationException {
      if (d instanceof BigDecimal) {
          return new BigDecimal(Math.sin(((BigDecimal) d).doubleValue()));
      } else if (d instanceof BigInteger) {
          return new BigDecimal(Math.sin(((BigInteger) d).intValue()));
      } else throw new EvaluationException("Argument of sinus must be numeric");
	}
}
