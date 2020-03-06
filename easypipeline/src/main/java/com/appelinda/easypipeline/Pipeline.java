package com.appelinda.easypipeline;

import android.os.AsyncTask;

public class Pipeline extends WorkStation {

    private IPipelineResult pipelineResult;

    public Pipeline(IPipelineResult pipelineResult, Integer pipelineRequestCode) {
        IsRoot = true;
        this.pipelineRequestCode = pipelineRequestCode;
        this.pipelineResult = pipelineResult;
    }

    public Pipeline(IPipelineResult pipelineResult, Integer pipelineRequestCode,IPipelineProgress iPipelineProgress) {
        IsRoot = true;
        this.pipelineRequestCode = pipelineRequestCode;
        this.pipelineResult = pipelineResult;
        this.iPipelineProgress = iPipelineProgress;
    }

    protected void InvokeAsync(IPipelineData iPipelineData) {
        new runInBackground().execute(iPipelineData);
    }

    private void invokeAsync(IPipelineData iPipelineData){
        super.InvokeAsync(iPipelineData);
    }

    private class runInBackground extends AsyncTask<IPipelineData, Void, IPipelineData> {
        @Override
        protected IPipelineData doInBackground(IPipelineData... iPipelineData) {
            invokeAsync(iPipelineData[0]);
            return iPipelineData[0];
        }

        @Override
        protected void onPostExecute(IPipelineData pipelineData) {
            pipelineResult.OnResult(pipelineRequestCode,pipelineData);
        }
    }

}
