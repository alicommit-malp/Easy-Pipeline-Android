package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStation4 extends WorkStation {

    @Override
    protected void Invoke(IPipelineData data) throws Exception {

        PipelineData pipelineData = (PipelineData) data;

        Thread.sleep(1000);
        pipelineData.AddResult(WorkStation4.class.getName());

        super.Invoke(data);
    }
}
