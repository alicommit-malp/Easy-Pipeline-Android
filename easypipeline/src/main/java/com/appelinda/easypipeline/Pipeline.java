package com.appelinda.easypipeline;

import android.os.AsyncTask;

import com.appelinda.easypipeline.data.PipelineResult;
import com.appelinda.easypipeline.interfaces.IPipelineData;

public class Pipeline extends WorkStation {

    /**
     * Will instantiate a new Pipeline with the pipeline data
     * @param pipelineData set the pipeline data which will travel through all the workstations
     */
    public Pipeline(IPipelineData pipelineData) {
        this.pipelineData = pipelineData;
        IsRoot = true;
    }

    /**
     * Will instantiate a new Pipeline with out any pipeline data
     */
    public Pipeline() {
        IsRoot = true;
    }

    protected void invoke(IPipelineData pipelineData) throws Exception {
        if (runOnUiThread) _invoke(pipelineData);
        else
            new PipelineWorker().execute(this);
    }

    private void _invoke(IPipelineData pipelineData) throws Exception {
        super.invoke(pipelineData);
    }

    /**
     * The Worker thread which will run the pipeline logic in Asynchronous mode
     */
    static class PipelineWorker extends AsyncTask<Pipeline, Void, Pipeline> {
        PipelineResult pipelineResult;

        @Override
        protected Pipeline doInBackground(Pipeline... pipelines) {
            pipelineResult = new PipelineResult(pipelines[0].pipelineRequestCode);
            try {
                Thread.sleep(pipelines[0].delayMillis);
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
