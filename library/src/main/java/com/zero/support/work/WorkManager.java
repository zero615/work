package com.zero.support.work;


public class WorkManager {

    public static WorkManager getDefault() {
        return manager;
    }

    @SuppressWarnings("ALL")
    public <T extends Task> T optTask(Class<T> task) {
        return (T) internal.opt(task);
    }

    @SuppressWarnings("ALL")
    public <T extends Task> T getTask(Class<T> task) {
        return (T) internal.get(task);
    }


    @SuppressWarnings("ALL")
    private class Internal extends TaskManager<Class, Task> {
        public Internal() {
            super(new Creator<Class, Task>() {
                @Override
                public Task creator(Class key) {
                    try {
                        return (Task) key.newInstance();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        @Override
        protected Task onCreateValue(Class key) {
            return creator.creator(key).observerOn(AppExecutor.main());
        }

        @Override
        protected void onBindValue(Task value, Object extra) {
            if (!value.isExecuted()) {
                value.input(extra).run(AppExecutor.async());
            }
        }
    }

    private final static WorkManager manager = new WorkManager();

    private final Internal internal = new Internal();

    public <T extends Task> T enqueue(Class<T> cls, Object extra) {
        return (T) internal.opt(cls, extra);
    }
}
