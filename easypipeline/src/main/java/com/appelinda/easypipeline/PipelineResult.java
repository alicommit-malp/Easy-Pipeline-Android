package com.appelinda.easypipeline;

public class PipelineResult {
    private Integer requestCode;
    private IPipelineData pipelineData;
    private boolean success=true;
    private Exception exception;

    public PipelineResult(Integer requestCode){
        this.requestCode = requestCode;
    }

    public Integer getRequestCode() {
        return requestCode;
    }

    public IPipelineData getPipelineData() {
        return pipelineData;
    }

    void setPipelineData(IPipelineData pipelineData) {
        this.pipelineData = pipelineData;
    }

    public boolean isSuccess() {
        return success;
    }

    public Exception getException() {
        return exception;
    }

    void setException(Exception exception) {
        this.success =false;
        this.exception = exception;
    }
}
