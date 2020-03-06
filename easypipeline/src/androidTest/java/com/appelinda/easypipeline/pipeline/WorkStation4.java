package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStation4 extends WorkStation {

    @Override
    protected void InvokeAsync(IPipelineData data) {

        PipelineData pipelineData = (PipelineData) data;

        try {
            Thread.sleep(1000);
            pipelineData.AddResult(WorkStation4.class.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.InvokeAsync(data);
    }
}
