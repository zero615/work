package com.zero.support.work;


public abstract class PromiseTask<Param, Result> extends Task<Param, Response<Result>> {

    @Override
    public Response<Result> doWork(Param input) {
        try {
            return Response.success(process(input));
        } catch (Throwable e) {
            e.printStackTrace();
            return Response.error(WorkExceptionConverter.convert(e), e.getMessage(), e);
        }
    }

    protected abstract Result process(Param input) throws Throwable;
}
