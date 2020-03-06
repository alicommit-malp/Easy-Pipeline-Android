package com.appelinda.easypipeline;

public abstract class WorkStation {

    WorkStation _nextWorkStation;
    WorkStation _prevWorkStation;
    boolean IsRoot;
    IPipelineProgress iPipelineProgress;
    Integer pipelineRequestCode = null;
    Integer workStationRequestCode = null;
    Float progressValue = null;

    /*
     *
     * */
    public WorkStation Next(WorkStation workStation, Float progress) {
        progressValue = progress;
        return _Next(workStation);
    }

    public WorkStation Next(WorkStation workStation, Integer _workStationRequestCode) {
        workStationRequestCode = _workStationRequestCode;

        return _Next(workStation);
    }

    public WorkStation Next(WorkStation workStation, Float progress, Integer _workStationRequestCode) {
        progressValue = progress;
        workStationRequestCode = _workStationRequestCode;

        return _Next(workStation);
    }

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

    public void Run(IPipelineData data) {
        if (IsRoot) {
            InvokeAsync(data);
        } else {
            _prevWorkStation.Run(data);
        }
    }

    void updateProgress() {
        iPipelineProgress.OnProgress(pipelineRequestCode, workStationRequestCode, progressValue);
    }

    protected void InvokeAsync(IPipelineData data) {
        if (progressValue != null || workStationRequestCode != null)
            updateProgress();

        if (_nextWorkStation != null) _nextWorkStation.InvokeAsync(data);
    }
}
