package com.paracamplus.ilp2.ilp2tme3;

import java.math.BigInteger;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.UnaryPrimitive;

public class VectorLength extends UnaryPrimitive {
    public VectorLength() {
        super("vectorLength");
    }

	@Override
	public Object apply(Object v) throws EvaluationException {
      if (v instanceof Object[]) {
          return BigInteger.valueOf(((Object[]) v).length);
      } else throw new EvaluationException("Argument of vectorLength must be a vector");
	}
}
