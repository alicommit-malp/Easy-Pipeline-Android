package com.appelinda.easypipeline;

public abstract class WorkStation {
    private WorkStation _nextWorkStation;
    private WorkStation _prevWorkStation;
    protected boolean IsRoot;

    public WorkStation Next(WorkStation workStation) {
        _nextWorkStation = workStation;
        _nextWorkStation._prevWorkStation = this;
        return _nextWorkStation;
    }

    public void Run(IPipelineData data) {
        if (IsRoot)
            InvokeAsync(data);
        else {
            _prevWorkStation.Run(data);
        }
    }

    protected void InvokeAsync(IPipelineData data) {
        if (_nextWorkStation != null) _nextWorkStation.InvokeAsync(data);
    }


}
