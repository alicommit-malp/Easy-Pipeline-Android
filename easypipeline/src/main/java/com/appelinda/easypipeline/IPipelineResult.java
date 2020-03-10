package com.appelinda.easypipeline;

/**
 * Pipeline's callback for the result of the pipeline
 */
public interface IPipelineResult extends IPipelineCallback {
    void OnResult(PipelineResult result);
}
