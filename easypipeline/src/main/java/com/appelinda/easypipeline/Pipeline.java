package com.appelinda.easypipeline;

import android.os.AsyncTask;

public class Pipeline extends WorkStation {

    private IPipelineResult pipelineResultCallback;
    private IPipelineData pipelineData;

    /**
     * Will instantiate a new Pipeline
     *
     * @param pipelineResultCallback      An instance reference to the IPipelineResult concrete implementation
     * @param pipelineRequestCode An unique request code belong to this pipeline
     */
    public Pipeline(IPipelineResult pipelineResultCallback, Integer pipelineRequestCode) {
        IsRoot = true;
        this.pipelineRequestCode = pipelineRequestCode;
        this.pipelineResultCallback = pipelineResultCallback;
    }

    /**
     * Will instantiate a new Pipeline
     *
     * @param pipelineResultCallback      An instance reference to the IPipelineResult concrete implementation
     * @param pipelineRequestCode An unique request code belong to this pipeline
     * @param iPipelineProgress   An instance reference to the IPipelineProgress concrete implantation
     */
    public Pipeline(IPipelineResult pipelineResultCallback, Integer pipelineRequestCode, IPipelineProgress iPipelineProgress) {
        IsRoot = true;
        this.pipelineRequestCode = pipelineRequestCode;
        this.pipelineResultCallback = pipelineResultCallback;
        this.iPipelineProgress = iPipelineProgress;
    }

    protected void InvokeAsync(IPipelineData pipelineData) {
        this.pipelineData = pipelineData;
        new PipelineWorker().execute(this);
    }

    private void invokeAsync(IPipelineData iPipelineData) throws Exception {
        super.InvokeAsync(iPipelineData);
    }

    static class PipelineWorker extends AsyncTask<Pipeline, Void, Pipeline> {
        PipelineResult pipelineResult;

        @Override
        protected Pipeline doInBackground(Pipeline... pipelines) {
            pipelineResult= new PipelineResult(pipelines[0].pipelineRequestCode);
            try {
                pipelines[0].invokeAsync(pipelines[0].pipelineData);
            } catch (Exception e) {
                pipelineResult.setException(e);
            } finally {
                pipelineResult.setPipelineData(pipelines[0].pipelineData);
            }
            return pipelines[0];
        }

        @Override
        protected void onPostExecute(Pipeline pipeline) {
            pipeline.pipelineResultCallback.OnResult(pipelineResult);
        }
    }
}
