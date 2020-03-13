package com.appelinda.easypipeline;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.appelinda.easypipeline.pipeline.PipelineData;
import com.appelinda.easypipeline.pipeline.WorkStation1;
import com.appelinda.easypipeline.pipeline.WorkStation2;
import com.appelinda.easypipeline.pipeline.WorkStation3;
import com.appelinda.easypipeline.pipeline.WorkStation4;
import com.appelinda.easypipeline.pipeline.WorkStationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PipelineTest implements IPipelineResult, IPipelineProgress {

    PipelineData pipelineData;
    public static final Integer ReqCode = 1;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    PipelineResult pipelineResult;

    @Test
    public void SyncNoDataTest() throws Exception {

        IPipelineData result = new Pipeline()
                .next(new WorkStation1())
                .next(new WorkStation2())
                .next(new WorkStation3())
                .next(new WorkStation4())
                .runOnUiThread();

        Assert.assertEquals(result, null);
    }

    @Test
    public void SyncTest() throws Exception {

        pipelineData = new PipelineData();

        PipelineData result = (PipelineData) new Pipeline(pipelineData)
                .next(new WorkStation1())
                .next(new WorkStation2())
                .next(new WorkStation3())
                .next(new WorkStation4())
                .runOnUiThread();

        Assert.assertEquals(result.getResult().size(), 4);
    }


    @Test
    public void AsyncNoDataTest() throws InterruptedException {

        new Pipeline()
                .next(new WorkStation1(), 20F, 11)
                .next(new WorkStation2())
                .next(new WorkStation3(), 12)
                .next(new WorkStation4(), 100F)
                .runAsync(this, ReqCode);

        countDownLatch.await();

        Assert.assertEquals(pipelineResult.getPipelineData(), null);
        Assert.assertEquals(pipelineResult.getRequestCode(), ReqCode);
        Assert.assertEquals(pipelineResult.isSuccess(), true);
    }


    @Test
    public void AsyncTestWithException() throws InterruptedException {

        pipelineData = new PipelineData();

        new Pipeline(pipelineData)
                .next(new WorkStation1(), 20F, 11)
                .next(new WorkStation2())
                .next(new WorkStationException())
                .next(new WorkStation3(), 12)
                .next(new WorkStation4(), 100F)
                .runAsync(this, ReqCode);

        countDownLatch.await();

        Assert.assertEquals(((PipelineData) (pipelineResult.getPipelineData())).getResult().size(), 2);
        Assert.assertEquals(pipelineResult.getRequestCode(), ReqCode);
        Assert.assertEquals(pipelineResult.isSuccess(), false);
        Assert.assertNotEquals(pipelineResult.getException(), null);
    }


    @Test
    public void AsyncTest() throws InterruptedException {

        pipelineData = new PipelineData();

        new Pipeline(pipelineData)
                .next(new WorkStation1(), 20F, 11)
                .next(new WorkStation2())
                .next(new WorkStation3(), 12)
                .next(new WorkStation4(), 100F)
                .runAsync(this, ReqCode);

        countDownLatch.await();

        Assert.assertEquals(((PipelineData) (pipelineResult.getPipelineData())).getResult().size(), 4);
        Assert.assertEquals(pipelineResult.getRequestCode(), ReqCode);
        Assert.assertEquals(pipelineResult.isSuccess(), true);
    }

    @Test
    public void AsyncNoRequestCodeTest() throws InterruptedException {

        pipelineData = new PipelineData();

        new Pipeline(pipelineData)
                .next(new WorkStation1(), 20F, 11)
                .next(new WorkStation2())
                .next(new WorkStation3(), 12)
                .next(new WorkStation4(), 100F)
                .runAsync(this);

        countDownLatch.await();

        Assert.assertEquals(((PipelineData) (pipelineResult.getPipelineData())).getResult().size(), 4);
        Assert.assertEquals(pipelineResult.getRequestCode(), null);
        Assert.assertEquals(pipelineResult.isSuccess(), true);
    }

    @Test
    public void AsyncNoRequestCodeNoCallbackTest() throws InterruptedException {

        pipelineData = new PipelineData();

        new Pipeline(pipelineData)
                .next(new WorkStation1(), 20F, 11)
                .next(new WorkStation2())
                .next(new WorkStation3(), 12)
                .next(new WorkStation4(), 100F)
                .runAsync();

        countDownLatch.await(10, TimeUnit.SECONDS);

        Assert.assertEquals(pipelineData.getResult().size(), 4);
    }

    @Test
    public void AsyncDelayTest() throws InterruptedException {

        pipelineData = new PipelineData();

        new Pipeline(pipelineData)
                .next(new WorkStation1(), 20F, 11)
                .next(new WorkStation2())
                .next(new WorkStation3(), 12)
                .next(new WorkStation4(), 100F)
                .runAsync(this, ReqCode, 2000);

        countDownLatch.await();

        Assert.assertEquals(((PipelineData) (pipelineResult.getPipelineData())).getResult().size(), 4);
        Assert.assertEquals(pipelineResult.getRequestCode(), ReqCode);
        Assert.assertEquals(pipelineResult.isSuccess(), true);
    }

    @Override
    public void OnResult(PipelineResult result) {
        if (result.isSuccess()) {
            Log.d("REQ-" + ReqCode, String.valueOf(result.getPipelineData()));
        } else {
            result.getException().printStackTrace();
        }

        pipelineResult = result;
        countDownLatch.countDown();
    }


    @Override
    public void OnProgress(Integer sourcePipelineHashCode, Integer workStationRequestCode, Float progress) {
        Log.d("REQ-" + sourcePipelineHashCode, " WorkStationReqCode: " + workStationRequestCode + " progress: " + progress);
    }
}
