package com.appelinda.easypipeline.interfaces;

import com.appelinda.easypipeline.data.PipelineResult;

/**
 * Pipeline's callback for the result of the pipeline
 */
public interface IPipelineResult extends IPipelineCallback {
    void OnResult(PipelineResult result);
}
