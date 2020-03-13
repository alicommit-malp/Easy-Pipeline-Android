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

new Pipeline(pipelineData)
        .Next(new Call_API_A(),25F)
        .Next(new Save_To_Local_DB(),50F)
        .Next(new Notify_The_UI(),75F)
        .Next(new Send_Statistics(),100F)
        .RunAsync(this,RequestCode);


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
    protected void invoke(IPipelineData data) throws Exception {

        PipelineData pipelineData = (PipelineData) data;

        //do the work

        super.invoke(data);
    }
}

```

therefore for each step or workstation you will create a class which must inherit from the WorkStation class and the PipelineData object will be available to it.

## Request Code

is an integer which you need to provide the pipeline with, to be able to handle the pipeline's tasks asynchronous, therefore when the pipeline finishes its work ,means that the last workstation has finished its work then the pipeline will call its callback with its request code , therefore you can have multiple pipelines in one class.

You can use it synchronously like this

```java

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



```

or Asynchronously like this

```java

@Test
public void AsyncTest() throws InterruptedException {

    pipelineData = new PipelineData();

    new Pipeline(pipelineData)
            .next(new WorkStation1(), /*progress=*/ 20F, /*requestCode=*/ 11)
            .next(new WorkStation2())
            .next(new WorkStation3(), /*progress=*/ 12F)
            .next(new WorkStation4(), /*progress=*/ 100F)
            .runAsync(this, ReqCode);

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

```

you can find [here]("https://github.com/alicommit-malp/Easy-Pipeline-Android/blob/master/easypipeline/src/androidTest/java/com/appelinda/easypipeline/PipelineTest.java") the rest of the test cases

Happy coding :)

>Find the Maven Repository [here](https://bintray.com/alicommit-malp/Android/Easy-Pipeline)
>Find the source code [here](https://github.com/alicommit-malp/Easy-Pipeline-Android)
