package com.appelinda.easypipeline;

/**
 * Pipeline's callback for the result of the pipeline
 */
public interface IPipelineResult {
    void OnResult(Integer pipelineRequestCode, IPipelineData pipelineData);
}
