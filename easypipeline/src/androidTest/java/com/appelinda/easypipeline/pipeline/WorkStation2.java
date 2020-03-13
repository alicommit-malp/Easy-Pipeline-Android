package com.appelinda.easypipeline.pipeline;

import com.appelinda.easypipeline.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStation2 extends WorkStation {

    @Override
    protected void invoke(IPipelineData data) throws Exception {

        if (data != null) {
            PipelineData pipelineData = (PipelineData) data;
            pipelineData.AddResult(WorkStation2.class.getName());
        }

        Thread.sleep(1000);
        super.invoke(data);
    }
}
