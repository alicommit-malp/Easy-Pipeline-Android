package com.appelinda.easypipeline.pipeline;

import com.appelinda.easypipeline.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStation2 extends WorkStation {
    @Override
    protected void InvokeAsync(IPipelineData data) {

        PipelineData pipelineData = (PipelineData) data;

        try {
            Thread.sleep(2000);
            pipelineData.setW2(WorkStation2.class.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.InvokeAsync(data);
    }
}
