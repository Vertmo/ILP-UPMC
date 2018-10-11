package com.paracamplus.ilp2.ilp2tme3;

import java.math.BigInteger;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp1.interpreter.primitive.Primitive;

public class MakeVector extends Primitive {
    public MakeVector() {
        super("makeVector");
    }

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public Object apply(Object lo, Object v) throws EvaluationException {
      if (lo instanceof BigInteger) {
          int l = ((BigInteger) lo).intValue();
          Object[] res = new Object[l];
          for(int i = 0; i < l; i++) {
              res[i] = v;
          }
          return res;
      } else throw new EvaluationException("First argument of makeVector must be an integer");
	}
}
