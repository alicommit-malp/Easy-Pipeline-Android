package com.appelinda.easypipeline;

import android.os.AsyncTask;

public class Pipeline extends WorkStation {

    private IPipelineResult pipelineResult;
    private int requestCode;

    public Pipeline(IPipelineResult pipelineResult, int requestCode) {
        IsRoot = true;
        this.requestCode = requestCode;
        this.pipelineResult = pipelineResult;
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
            pipelineResult.OnResult(requestCode,pipelineData);
        }
    }

}
