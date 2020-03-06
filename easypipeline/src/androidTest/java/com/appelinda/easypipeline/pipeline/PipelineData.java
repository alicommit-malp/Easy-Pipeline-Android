package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.IPipelineData;

import java.util.ArrayList;
import java.util.List;

public class PipelineData implements IPipelineData {
    private List<String> result= new ArrayList<>();

    public List<String> getResult() {
        return result;
    }

    public void AddResult(String value) {
        result.add(value);
    }
}
