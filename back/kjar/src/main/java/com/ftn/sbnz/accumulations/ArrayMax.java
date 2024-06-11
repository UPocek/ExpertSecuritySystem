package com.ftn.sbnz.accumulations;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import com.ftn.sbnz.model.models.AggregatedDetection;

public class ArrayMax implements AccumulateFunction {

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    public static class ArrayMaxContext implements Externalizable {
        public AggregatedDetection maxAgg = null;
        public Double maxValue = Double.MIN_VALUE;

        public ArrayMaxContext() {

        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            maxAgg = (AggregatedDetection) in.readObject();
            maxValue = in.readDouble();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(maxAgg);
            out.writeDouble(maxValue);
        }
    }

    @Override
    public Serializable createContext() {
        return new ArrayMaxContext();
    }

    @Override
    public void init(Serializable context) throws Exception {
    }

    @Override
    public void accumulate(Serializable context, Object value) {
        ArrayMaxContext c = (ArrayMaxContext) context;

        AggregatedDetection agg = (AggregatedDetection) value;
        if (agg.getAggregation().getMax() > c.maxValue) {
            c.maxValue = agg.getAggregation().getMax();
            c.maxAgg = agg;
        }
    }

    @Override
    public void reverse(Serializable context, Object value) throws Exception {
    }

    @Override
    public Object getResult(Serializable context) throws Exception {
        return ((ArrayMaxContext) context).maxValue;
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
