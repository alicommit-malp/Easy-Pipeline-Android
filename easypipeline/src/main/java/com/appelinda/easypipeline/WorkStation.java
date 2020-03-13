package com.appelinda.easypipeline;

/**
 * @author Ali Alp
 * @see <a href="https://dev.to/alialp/asynchronous-easy-pipeline-in-android-h7p">Easy-Pipeline on Dev.to</a>
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
    IPipelineCallback pipelineCallback;
    IPipelineData pipelineData;
    boolean runOnUiThread = false;

    /**
     * Set the next WorkStation in the pipeline
     *
     * @param workStation an instance of a concrete implementation of WorkStation abstract class
     * @param progress    the amount of the progress which is considered done when this WorkStation's job is done
     * @return returning the same instance of the workStation for supporting builder patternA
     */
    public WorkStation next(WorkStation workStation, Float progress) {
        progressValue = progress;
        return _Next(workStation);
    }

    /**
     * Set the next WorkStation in the pipeline
     *
     * @param workStation             an instance of a concrete implementation of WorkStation abstract class
     * @param _workStationRequestCode a request code which must be unique in this pipeline,
     *                                Pipeline will notify the caller method's OnProgress() callback method providing
     *                                this request code to be able to determine which WorkStation has been done
     * @return returning the same instance of the workStation for supporting builder pattern
     */
    public WorkStation next(WorkStation workStation, Integer _workStationRequestCode) {
        workStationRequestCode = _workStationRequestCode;

        return _Next(workStation);
    }

    /**
     * Set the next WorkStation in the pipeline
     *
     * @param workStation             an instance of a concrete implementation of WorkStation abstract class
     * @param progress                the amount of the progress which is considered done when this WorkStation's job is done
     * @param _workStationRequestCode a request code which must be unique in this pipeline,
     *                                Pipeline will notify the caller method's OnProgress() callback method providing
     *                                this request code to be able to determine which WorkStation has been done
     * @return returning the same instance of the workStation for supporting builder pattern
     */
    public WorkStation next(WorkStation workStation, Float progress, Integer _workStationRequestCode) {
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
    public WorkStation next(WorkStation workStation) {
        return _Next(workStation);
    }


    private WorkStation _Next(WorkStation workStation) {
        _nextWorkStation = workStation;
        _nextWorkStation._prevWorkStation = this;
        _nextWorkStation.iPipelineProgress = iPipelineProgress;
        _nextWorkStation.pipelineRequestCode = pipelineRequestCode;
        _nextWorkStation.pipelineData = pipelineData;

        return _nextWorkStation;
    }

    /**
     * Asynchronously, Begin the Pipeline by traversing from the first defined Workstation
     *
     */
    public void runAsync() {
        if (IsRoot) {
            try {
                invoke(pipelineData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            _prevWorkStation.runAsync();
        }
    }

    /**
     * Synchronously, Begin the Pipeline by traversing from the first defined Workstation
     */
    public IPipelineData runOnUiThread() throws Exception {
        this.runOnUiThread = true;
        if (IsRoot) {
            invoke(pipelineData);
        } else {
            _prevWorkStation.runOnUiThread();
        }
        return pipelineData;
    }

    void updateProgress() {
        if (iPipelineProgress != null)
            iPipelineProgress.OnProgress(pipelineRequestCode, workStationRequestCode, progressValue);
    }

    protected void invoke(IPipelineData data) throws Exception {
        if (progressValue != null || workStationRequestCode != null)
            updateProgress();

        if (_nextWorkStation != null) _nextWorkStation.invoke(data);
    }
}
