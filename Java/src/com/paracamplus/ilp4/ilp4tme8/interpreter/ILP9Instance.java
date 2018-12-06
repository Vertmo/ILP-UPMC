package com.paracamplus.ilp4.ilp4tme8.interpreter;

import java.util.HashMap;
import java.util.Map;

import com.paracamplus.ilp1.interpreter.interfaces.EvaluationException;
import com.paracamplus.ilp4.interpreter.interfaces.IClass;

public class ILP9Instance extends com.paracamplus.ilp4.interpreter.ILP9Instance {

    public ILP9Instance (IClass clazz, Object[] fields)
        throws EvaluationException {
        super(clazz, fields);
        this.fields = new HashMap<>();
        if ( fields.length != clazz.getTotalFieldCount() ) {
            String msg = "Wrong number of initial values for "
                + clazz.getName();
            throw new EvaluationException(msg);
        }
        String[] fieldNames = this.classOf().getTotalFieldNames();
        for(int i = 0; i < fieldNames.length; i++) {
            this.fields.put(fieldNames[i], fields[i]);
        }
    }
    private final Map<String, Object> fields;

    @Override
    public Object read(String fieldName)
        throws EvaluationException {
        return this.fields.get(fieldName);
    }

    @Override
    public Object write(String fieldName, Object value)
        throws EvaluationException {
        Object old = this.fields.get(fieldName);
        this.fields.put(fieldName, value);
        return old;
    }

}
