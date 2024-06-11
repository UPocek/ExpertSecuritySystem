package com.ftn.sbnz.accumulations;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import com.ftn.sbnz.model.models.AggregateProduct;

public class ArrayCountProduct implements AccumulateFunction {

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    public static class ArrayCountContext implements Externalizable {
        public Long countValue = 0L;

        public ArrayCountContext() {

        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            countValue = in.readLong();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeLong(countValue);
        }
    }

    @Override
    public Serializable createContext() {
        return new ArrayCountContext();
    }

    @Override
    public void init(Serializable context) throws Exception {
    }

    @Override
    public void accumulate(Serializable context, Object value) {
        ArrayCountContext c = (ArrayCountContext) context;

        AggregateProduct valueAggregated = (AggregateProduct) value;
        c.countValue += valueAggregated.getQuantity();
    }

    @Override
    public void reverse(Serializable context, Object value) throws Exception {
    }

    @Override
    public Object getResult(Serializable context) throws Exception {
        return ((ArrayCountContext) context).countValue;
    }

    @Override
    public boolean supportsReverse() {
        return false;
    }

    @Override
    public Class<?> getResultType() {
        return Long.class;
    }

}
