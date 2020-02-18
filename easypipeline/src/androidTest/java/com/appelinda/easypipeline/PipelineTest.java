package com.appelinda.easypipeline;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.appelinda.easypipeline.pipeline.PipelineData;
import com.appelinda.easypipeline.pipeline.WorkStation1;
import com.appelinda.easypipeline.pipeline.WorkStation2;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PipelineTest implements IPipelineResult {

    PipelineData pipelineData;
    public static final int ReqCode = 1;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        pipelineData  = new PipelineData();

        new Pipeline(this,ReqCode)
                .Next(new WorkStation1())
                .Next(new WorkStation2())
                .Run(pipelineData);

    }

    @Override
    public void OnResult(int sourcePipelineHashCode, IPipelineData pipelineData) {
       switch (sourcePipelineHashCode){
           case ReqCode:
               PipelineData data = (PipelineData) pipelineData;
               Log.d("REQ-" + ReqCode , data.toString());
               break;
       }
    }
}
