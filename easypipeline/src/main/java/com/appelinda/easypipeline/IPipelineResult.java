package com.appelinda.easypipeline;

public interface IPipelineResult {

    void OnResult(Integer pipelineRequestCode, IPipelineData pipelineData);
}
