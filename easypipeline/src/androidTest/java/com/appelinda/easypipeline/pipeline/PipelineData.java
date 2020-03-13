package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.interfaces.IPipelineData;

import java.util.ArrayList;
import java.util.List;

public class PipelineData implements IPipelineData {
    private List<String> result;

    public List<String> getResult() {
        return result;
    }

    public void AddResult(String value) {
        if(result==null) result = new ArrayList<>();
        result.add(value);
    }

    @Override
    public String toString() {
        return "PipelineData{" +
                "result=" + result +
                '}';
    }
}
