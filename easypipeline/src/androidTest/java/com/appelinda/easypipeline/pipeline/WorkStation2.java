package com.appelinda.easypipeline.pipeline;

import com.appelinda.easypipeline.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStation2 extends WorkStation {
    @Override
    protected void InvokeAsync(IPipelineData data) throws Exception {

        PipelineData pipelineData = (PipelineData) data;

        Thread.sleep(2000);
        pipelineData.AddResult(WorkStation2.class.getName());

        super.InvokeAsync(data);
    }
}
