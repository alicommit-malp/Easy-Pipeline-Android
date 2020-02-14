package com.appelinda.easypipeline;

public interface IPipelineResult {
    void OnResult(int sourcePipelineHashCode, IPipelineData pipelineData);
}
