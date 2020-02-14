package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStation1 extends WorkStation {

    @Override
    protected void InvokeAsync(IPipelineData data) {

        PipelineData pipelineData = (PipelineData) data;

        try {
            Thread.sleep(1000);
            pipelineData.setW1(WorkStation1.class.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.InvokeAsync(data);
    }
}
