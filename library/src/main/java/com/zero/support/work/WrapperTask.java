package com.zero.support.work;

public class WrapperTask<Param, Result> extends SnapShotTask<Param, Result> {
    private Class<?> task;

    public WrapperTask(Class<?> task) {
        this.task = task;
    }


    @Override
    protected Result process(Param input) throws Exception {
        return null;
    }

}
