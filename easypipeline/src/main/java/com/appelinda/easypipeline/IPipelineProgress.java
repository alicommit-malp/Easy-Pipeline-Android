package com.appelinda.easypipeline;

public interface IPipelineProgress {

    void OnProgress(Integer sourcePipelineHashCode, Integer workStationRequestCode,Float progress);
}
