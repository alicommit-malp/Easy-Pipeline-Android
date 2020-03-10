package com.appelinda.easypipeline;

class PipelineRequest {
    private IPipelineData pipelineData;
    private IPipelineProgress pipelineProgress;
    private IPipelineResult pipelineResult;
    private Integer pipelineRequestCode;

    public IPipelineData getPipelineData() {
        return pipelineData;
    }

    public void setPipelineData(IPipelineData pipelineData) {
        this.pipelineData = pipelineData;
    }

    public IPipelineProgress getPipelineProgress() {
        return pipelineProgress;
    }

    public void setPipelineProgress(IPipelineProgress pipelineProgress) {
        this.pipelineProgress = pipelineProgress;
    }

    public IPipelineResult getPipelineResult() {
        return pipelineResult;
    }

    public void setPipelineResult(IPipelineResult pipelineResult) {
        this.pipelineResult = pipelineResult;
    }

    public Integer getPipelineRequestCode() {
        return pipelineRequestCode;
    }

    public void setPipelineRequestCode(Integer pipelineRequestCode) {
        this.pipelineRequestCode = pipelineRequestCode;
    }
}
