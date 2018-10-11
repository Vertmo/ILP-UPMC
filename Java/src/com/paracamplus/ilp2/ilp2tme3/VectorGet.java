package com.paracamplus.ilp2.ilp2tme3;

import java.math.BigInteger;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.Primitive;

public class VectorGet extends Primitive {
    public VectorGet() {
        super("vectorGet");
    }

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public Object apply(Object vo, Object io) throws EvaluationException {
      if (vo instanceof Object[]) {
          Object v[] = (Object[]) vo;
          if (io instanceof BigInteger) {
              int i = ((BigInteger) io).intValue();
              try {
                  return v[i];
              } catch(ArrayIndexOutOfBoundsException e) {
                  throw new EvaluationException("vectorGet: index out of bound (" + i + ")");
              }
          } else throw new EvaluationException("Second argument of vectorGet must be an integer");
      } else throw new EvaluationException("First argument of vectorGet must be a vector");
	}
}
