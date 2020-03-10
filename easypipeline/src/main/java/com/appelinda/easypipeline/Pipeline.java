package com.appelinda.easypipeline;

import android.os.AsyncTask;

public class Pipeline extends WorkStation {

    private IPipelineResult pipelineResult;

    /**
     * Will instantiate a new Pipeline
     *
     * @param pipelineResult      An instance reference to the IPipelineResult concrete implementation
     * @param pipelineRequestCode An unique request code belong to this pipeline
     */
    public Pipeline(IPipelineResult pipelineResult, Integer pipelineRequestCode) {
        IsRoot = true;
        this.pipelineRequestCode = pipelineRequestCode;
        this.pipelineResult = pipelineResult;
    }

    /**
     * Will instantiate a new Pipeline
     *
     * @param pipelineResult      An instance reference to the IPipelineResult concrete implementation
     * @param pipelineRequestCode An unique request code belong to this pipeline
     * @param iPipelineProgress   An instance reference to the IPipelineProgress concrete implantation
     */
    public Pipeline(IPipelineResult pipelineResult, Integer pipelineRequestCode, IPipelineProgress iPipelineProgress) {
        IsRoot = true;
        this.pipelineRequestCode = pipelineRequestCode;
        this.pipelineResult = pipelineResult;
        this.iPipelineProgress = iPipelineProgress;
    }

    protected void InvokeAsync(IPipelineData iPipelineData) {
        new runInBackground().execute(iPipelineData);
    }

    void invokeAsync(IPipelineData iPipelineData) {
        super.InvokeAsync(iPipelineData);
    }

    class runInBackground extends AsyncTask<IPipelineData, Void, IPipelineData> {
        @Override
        protected IPipelineData doInBackground(IPipelineData... iPipelineData) {
            invokeAsync(iPipelineData[0]);
            return iPipelineData[0];
        }

        @Override
        protected void onPostExecute(IPipelineData pipelineData) {
            pipelineResult.OnResult(pipelineRequestCode, pipelineData);
        }
    }

}
