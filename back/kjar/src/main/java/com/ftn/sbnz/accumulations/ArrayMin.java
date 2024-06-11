package com.ftn.sbnz.accumulations;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import com.ftn.sbnz.model.models.AggregatedDetection;

public class ArrayMin implements AccumulateFunction {

    public static class ArrayMinContext implements Externalizable {
        public AggregatedDetection minAgg = null;
        public Double minValue = Double.MAX_VALUE;

        public ArrayMinContext() {

        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            minAgg = (AggregatedDetection) in.readObject();
            minValue = in.readDouble();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(minValue);
            out.writeDouble(minValue);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    @Override
    public Serializable createContext() {
        return new ArrayMinContext();
    }

    @Override
    public void init(Serializable context) throws Exception {
    }

    @Override
    public void accumulate(Serializable context, Object value) {
        ArrayMinContext c = (ArrayMinContext) context;

        AggregatedDetection agg = (AggregatedDetection) value;
        if (agg.getAggregation().getMin() < c.minValue) {
            c.minValue = agg.getAggregation().getMin();
            c.minAgg = agg;
        }
    }

    @Override
    public void reverse(Serializable context, Object value) throws Exception {
    }

    @Override
    public Object getResult(Serializable context) throws Exception {
        return ((ArrayMinContext) context).minValue;
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
