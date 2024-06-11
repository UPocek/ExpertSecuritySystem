package com.ftn.sbnz.accumulations;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import com.ftn.sbnz.model.models.AggregatedDetection;

public class ArrayAverage implements AccumulateFunction {

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    public static class ArrayAverageContext implements Externalizable {
        public Double sumValue = 0D;
        public Long countValue = 0L;

        public ArrayAverageContext() {

        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            sumValue = in.readDouble();
            countValue = in.readLong();
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeDouble(sumValue);
            out.writeLong(countValue);
        }
    }

    @Override
    public Serializable createContext() {
        return new ArrayAverageContext();
    }

    @Override
    public void init(Serializable context) throws Exception {
    }

    @Override
    public void accumulate(Serializable context, Object value) {
        ArrayAverageContext c = (ArrayAverageContext) context;

        AggregatedDetection valueAggregated = (AggregatedDetection) value;
        c.sumValue += valueAggregated.getAggregation().getSum();
        c.countValue += valueAggregated.getAggregation().getCount();
    }

    @Override
    public void reverse(Serializable context, Object value) throws Exception {
    }

    @Override
    public Object getResult(Serializable context) throws Exception {
        ArrayAverageContext c = (ArrayAverageContext) context;

        return c.sumValue / c.countValue;
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
