# Asynchronous Easy pipeline in Android

Lets suppose the following logic in an Android Application  

- Call an API_A
- Save the result in the local DB
- Notify the user Interface 
- send statistic to another API_B

We know that we have to take care of all these operations in separate thread. 

>Find the Maven Repository [here](https://bintray.com/alicommit-malp/Android/Easy-Pipeline)

>Find the source code [here](https://github.com/alicommit-malp/Easy-Pipeline-Android)


## Easy-Pipeline 
will take care of the above example as easy as the following code

```java

new Pipeline(this,ReqCode)
        .Next(new Call_API_A())
        .Next(new Save_To_Local_DB())
        .Next(new Notify_The_UI())
        .Next(new Send_Statistics())
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
    protected void InvokeAsync(IPipelineData data) {

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
public class PipelineTest implements IPipelineResult {

    PipelineData pipelineData;
    public static final int ReqCode = 1;

    @Test
    public void test() {
        
        pipelineData  = new PipelineData();

        new Pipeline(this,ReqCode)
                .Next(new Call_API_A())
                .Next(new Save_To_Local_DB())
                .Next(new Notify_The_UI())
                .Next(new Send_Statistics())
                .Run(pipelineData);

    }

    @Override
    public void OnResult(int sourcePipelineHashCode, IPipelineData pipelineData) {
       switch (sourcePipelineHashCode){
           case ReqCode:
               PipelineData data = (PipelineData) pipelineData;
               //use the result 
               break;
       }
    }
}

```

Happy coding :)


>Find the Maven Repository [here](https://bintray.com/alicommit-malp/Android/Easy-Pipeline)

>Find the source code [here](https://github.com/alicommit-malp/Easy-Pipeline-Android)
