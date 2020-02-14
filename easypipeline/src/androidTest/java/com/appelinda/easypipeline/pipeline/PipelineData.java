package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.IPipelineData;

public class PipelineData implements IPipelineData {
    private String W1;
    private String W2;

    public String getW1() {
        return W1;
    }

    public void setW1(String w1) {
        W1 = w1;
    }

    public String getW2() {
        return W2;
    }

    public void setW2(String w2) {
        W2 = w2;
    }

    @Override
    public String toString() {
        return "PipelineData{" +
                "W1='" + W1 + '\'' +
                ", W2='" + W2 + '\'' +
                '}';
    }
}
