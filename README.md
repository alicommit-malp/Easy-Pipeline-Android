# Asynchronous Easy pipeline in Android

Lets suppose the following logic in an Android Application  

- Call an API_A
- Save the result in the local DB
- Notify the user Interface
- send statistic to another API_B
- send back progress updates on each step to the caller

We know that we have to take care of all these operations in separate thread.

>Find the Maven Repository [here](https://bintray.com/alicommit-malp/Android/Easy-Pipeline)
>Find the source code [here](https://github.com/alicommit-malp/Easy-Pipeline-Android)

## Easy-Pipeline

will take care of the above example as easy as the following code

```java

new Pipeline(this,ReqCode,this)
        .Next(new Call_API_A(),25F)
        .Next(new Save_To_Local_DB(),50F)
        .Next(new Notify_The_UI(),75F)
        .Next(new Send_Statistics(),100F)
        .Run(pipelineData);


```

## PipelineData

is the object which will travel through all the Workstations 

```java

public class PipelineData implements IPipelineData {
    //have your properties here
}

```

## Workstation

A step in a pipeline, in the above example Call_API_A() is a workstation 

```java

public class Call_API_A extends WorkStation {

    @Override
    protected void InvokeAsync(IPipelineData data) throws Exception {

        PipelineData pipelineData = (PipelineData) data;

        //do the work

        super.InvokeAsync(data);
    }
}

```

therefore for each step or workstation you will create a class which must inherit from the WorkStation class and the PipelineData object will be available to it.

## Request Code

is an integer which you need to provide the pipeline with, to be able to handle the pipeline's tasks asynchronous, therefore when the pipeline finishes its work ,means that the last workstation has finished its work then the pipeline will call its callback with its request code , therefore you can have multiple pipelines in one class.

## Complete code

```java

@RunWith(AndroidJUnit4.class)
public class PipelineTest implements IPipelineResult, IPipelineProgress {

    PipelineData pipelineData;
    public static final int ReqCode = 1;
    private CountDownLatch lock = new CountDownLatch(1);
    PipelineData pipelineResult;


    @Test
    public void useAppContext() throws InterruptedException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        pipelineData = new PipelineData();

        new Pipeline(this, ReqCode, this)
                .Next(new WorkStation1(), 20F, 11)
                .Next(new WorkStation2())
                .Next(new WorkStation3(), 12)
                .Next(new WorkStation4(), 100F)
                .Run(pipelineData);

        lock.await();

        Assert.assertEquals(pipelineData,pipelineResult);
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
        }else {
           result.getException().printStackTrace();
        }

        lock.countDown();
    }


    @Override
    public void OnProgress(Integer sourcePipelineHashCode, Integer workStationRequestCode, Float progress) {
        Log.d("REQ-" + sourcePipelineHashCode, " WorkStationReqCode: " + workStationRequestCode + " progress: " + progress);
    }
}


```

Happy coding :)

>Find the Maven Repository [here](https://bintray.com/alicommit-malp/Android/Easy-Pipeline)
>Find the source code [here](https://github.com/alicommit-malp/Easy-Pipeline-Android)
