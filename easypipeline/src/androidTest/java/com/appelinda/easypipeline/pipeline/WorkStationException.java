package com.appelinda.easypipeline.pipeline;


import com.appelinda.easypipeline.interfaces.IPipelineData;
import com.appelinda.easypipeline.WorkStation;

public class WorkStationException extends WorkStation {

    @Override
    protected void invoke(IPipelineData data) throws Exception {
        throw new Exception();
    }
}
