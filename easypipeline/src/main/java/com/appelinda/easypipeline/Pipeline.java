package com.appelinda.easypipeline;

import android.os.AsyncTask;

public class Pipeline extends WorkStation {

    private IPipelineCallback pipelineCallback;
    private IPipelineData pipelineData;
    private IPipelineResult pipelineResult;


    /**
     * Will instantiate a new Pipeline
     */
    public Pipeline() {
        IsRoot = true;
    }


    /**
     * Will instantiate a new Pipeline
     *
     * @param pipelineCallback    An instance(s) reference to the IPipelineResult or/and IPipelineProgress concrete implementation
     * @param pipelineRequestCode An unique request code belong to this pipeline
     */
    public Pipeline(IPipelineCallback pipelineCallback, Integer pipelineRequestCode) {
        IsRoot = true;
        this.pipelineRequestCode = pipelineRequestCode;
        this.pipelineCallback = pipelineCallback;
        this.pipelineResult = (IPipelineResult) pipelineCallback;

        try {
            this.iPipelineProgress = (IPipelineProgress) pipelineCallback;
        } catch (ClassCastException e) {
            //ignore
        }
    }

    protected void invoke(IPipelineData pipelineData) throws Exception {
        this.pipelineData = pipelineData;
        if (runOnUiThread) _invoke(pipelineData);
        else
            new PipelineWorker().execute(this);
    }

    private void _invoke(IPipelineData iPipelineData) throws Exception {
        super.invoke(iPipelineData);
    }

    static class PipelineWorker extends AsyncTask<Pipeline, Void, Pipeline> {
        PipelineResult pipelineResult;

        @Override
        protected Pipeline doInBackground(Pipeline... pipelines) {
            pipelineResult = new PipelineResult(pipelines[0].pipelineRequestCode);
            try {
                pipelines[0]._invoke(pipelines[0].pipelineData);
            } catch (Exception e) {
                pipelineResult.setException(e);
            } finally {
                pipelineResult.setPipelineData(pipelines[0].pipelineData);
            }
            return pipelines[0];
        }

        @Override
        protected void onPostExecute(Pipeline pipeline) {
            if (pipeline.pipelineResult != null)
                pipeline.pipelineResult.OnResult(pipelineResult);
        }
    }
}
