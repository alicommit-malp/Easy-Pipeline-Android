package com.appelinda.easypipeline;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.appelinda.easypipeline.pipeline.PipelineData;
import com.appelinda.easypipeline.pipeline.WorkStation1;
import com.appelinda.easypipeline.pipeline.WorkStation2;
import com.appelinda.easypipeline.pipeline.WorkStation3;
import com.appelinda.easypipeline.pipeline.WorkStation4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PipelineTest implements IPipelineResult ,IPipelineProgress{

    PipelineData pipelineData;
    public static final int ReqCode = 1;
    private CountDownLatch lock = new CountDownLatch(1);
    PipelineData pipelineResult;

    @Test
    public void SyncTest() throws InterruptedException {

        pipelineData = new PipelineData();

        PipelineResult result = new Pipeline(this, ReqCode)
                .Next(new WorkStation1(), 20F, 11)
                .Next(new WorkStation2())
                .Next(new WorkStation3(), 12)
                .Next(new WorkStation4(), 100F)
                .RunOnUiThread(pipelineData);

        Assert.assertEquals(pipelineData, result.getPipelineData());
    }

    @Test
    public void AsyncTest() throws InterruptedException {

        pipelineData = new PipelineData();

        new Pipeline(this, ReqCode)
                .Next(new WorkStation1(), 20F, 11)
                .Next(new WorkStation2())
                .Next(new WorkStation3(), 12)
                .Next(new WorkStation4(), 100F)
                .Run(pipelineData);

        lock.await();

        Assert.assertEquals(pipelineData, pipelineResult);
    }

    @Override
    public void OnResult(PipelineResult result) {
        if (result.isSuccess()) {
            switch (result.getRequestCode()) {
                case ReqCode:
                    Log.d("REQ-" + ReqCode, result.getPipelineData().toString());
                    pipelineResult = (PipelineData) result.getPipelineData();
                    break;
            }
        } else {
            result.getException().printStackTrace();
        }

        lock.countDown();
    }


    @Override
    public void OnProgress(Integer sourcePipelineHashCode, Integer workStationRequestCode, Float progress) {
        Log.d("REQ-" + sourcePipelineHashCode, " WorkStationReqCode: " + workStationRequestCode + " progress: " + progress);
    }
}
