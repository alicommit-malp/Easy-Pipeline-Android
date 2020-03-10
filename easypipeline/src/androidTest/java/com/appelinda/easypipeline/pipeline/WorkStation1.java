package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStation1 extends WorkStation {

    @Override
    protected void InvokeAsync(IPipelineData data) throws Exception {

        PipelineData pipelineData = (PipelineData) data;

        Thread.sleep(1000);
        pipelineData.AddResult(WorkStation1.class.getName());


        super.InvokeAsync(data);
    }
}
