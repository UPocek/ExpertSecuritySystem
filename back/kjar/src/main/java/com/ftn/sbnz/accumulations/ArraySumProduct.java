package com.ftn.sbnz.accumulations;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import com.ftn.sbnz.model.models.AggregateProduct;

public class ArraySumProduct implements AccumulateFunction {

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    public static class ArraySumContext implements Externalizable {
        public Double sumValue = 0D;

        public ArraySumContext() {

        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            sumValue = in.readDouble();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeDouble(sumValue);
        }
    }

    @Override
    public Serializable createContext() {
        return new ArraySumContext();
    }

    @Override
    public void init(Serializable context) throws Exception {
    }

    @Override
    public void accumulate(Serializable context, Object value) {
        ArraySumContext c = (ArraySumContext) context;

        AggregateProduct valueAggregated = (AggregateProduct) value;
        c.sumValue += valueAggregated.getPrice();
    }

    @Override
    public void reverse(Serializable context, Object value) throws Exception {
    }

    @Override
    public Object getResult(Serializable context) throws Exception {
        return ((ArraySumContext) context).sumValue;
    }

    @Override
    public boolean supportsReverse() {
        return false;
    }

    @Override
    public Class<?> getResultType() {
        return Double.class;
    }

}
