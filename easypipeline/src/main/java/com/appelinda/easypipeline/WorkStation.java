package com.appelinda.easypipeline;

/**
 * @author Ali Alp
 * @see  <a href="https://dev.to/alialp/asynchronous-easy-pipeline-in-android-h7p">Easy-Pipeline on Dev.to</a>
 * Each step of the Pipeline (including the Pipeline class itself) must be inherited from
 * this class to be added as Next() method's parameter.
 */
public abstract class WorkStation {

    WorkStation _nextWorkStation;
    WorkStation _prevWorkStation;
    boolean IsRoot;
    IPipelineProgress iPipelineProgress;
    Integer pipelineRequestCode = null;
    Integer workStationRequestCode = null;
    Float progressValue = null;

    /**
     * Set the next WorkStation in the pipeline
     *
     * @param workStation an instance of a concrete implementation of WorkStation abstract class
     * @param progress    the amount of the progress which is considered done when this WorkStation's job is done
     * @return returning the same instance of the workStation for supporting builder patternA
     */
    public WorkStation Next(WorkStation workStation, Float progress) {
        progressValue = progress;
        return _Next(workStation);
    }

    /**
     * Set the next WorkStation in the pipeline
     *
     * @param workStation an instance of a concrete implementation of WorkStation abstract class
     * @param _workStationRequestCode  a request code which must be unique in this pipeline,
     *                                 Pipeline will notify the caller method's OnProgress() callback method providing
     *                                 this request code to be able to determine which WorkStation has been done
     * @return returning the same instance of the workStation for supporting builder pattern
     */
    public WorkStation Next(WorkStation workStation, Integer _workStationRequestCode) {
        workStationRequestCode = _workStationRequestCode;

        return _Next(workStation);
    }

    /**
     * Set the next WorkStation in the pipeline
     *
     * @param workStation an instance of a concrete implementation of WorkStation abstract class
     * @param progress    the amount of the progress which is considered done when this WorkStation's job is done
     * @param _workStationRequestCode  a request code which must be unique in this pipeline,
     *                                 Pipeline will notify the caller method's OnProgress() callback method providing
     *                                 this request code to be able to determine which WorkStation has been done
     * @return returning the same instance of the workStation for supporting builder pattern
     */
    public WorkStation Next(WorkStation workStation, Float progress, Integer _workStationRequestCode) {
        progressValue = progress;
        workStationRequestCode = _workStationRequestCode;

        return _Next(workStation);
    }


    /**
     * Set the next WorkStation in the pipeline
     *
     * @param workStation an instance of a concrete implementation of WorkStation abstract class
     * @return returning the same instance of the workStation for supporting builder pattern
     */
    public WorkStation Next(WorkStation workStation) {
        return _Next(workStation);
    }


    private WorkStation _Next(WorkStation workStation) {
        _nextWorkStation = workStation;
        _nextWorkStation._prevWorkStation = this;
        _nextWorkStation.iPipelineProgress = iPipelineProgress;
        _nextWorkStation.pipelineRequestCode = pipelineRequestCode;

        return _nextWorkStation;
    }

    /**
     * Begin the Pipeline by traversing from the first defined Workstation
     *
     * @param data an instance of a concrete implementation of the IPipelineData class
     */
    public void Run(IPipelineData data){
        if (IsRoot) {
            try {
                InvokeAsync(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            _prevWorkStation.Run(data);
        }
    }

    void updateProgress() {
        if (iPipelineProgress == null)
            throw new NullPointerException("You cannot provide progress value when calling the Next method and not provide the IPipelineProgress instance! ");
        iPipelineProgress.OnProgress(pipelineRequestCode, workStationRequestCode, progressValue);
    }

    protected void InvokeAsync(IPipelineData data) throws Exception{
        if (progressValue != null || workStationRequestCode != null)
            updateProgress();

        if (_nextWorkStation != null) _nextWorkStation.InvokeAsync(data);
    }
}
