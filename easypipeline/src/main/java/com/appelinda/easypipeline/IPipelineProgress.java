package com.appelinda.easypipeline;

/**
 * Pipeline's callback for notifying the caller on progress of the pipeline (end of each WorkStation)
 */
public interface IPipelineProgress {

    void OnProgress(Integer sourcePipelineHashCode, Integer workStationRequestCode,Float progress);
}
