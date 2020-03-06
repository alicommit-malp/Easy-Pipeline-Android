package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStation3 extends WorkStation {

    @Override
    protected void InvokeAsync(IPipelineData data) {

        PipelineData pipelineData = (PipelineData) data;

        try {
            Thread.sleep(1000);
            pipelineData.AddResult(WorkStation3.class.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.InvokeAsync(data);
    }
}
