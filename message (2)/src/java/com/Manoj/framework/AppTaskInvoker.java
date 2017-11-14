

package com.Manoj.framework;

import org.springframework.core.task.TaskExecutor;


public interface AppTaskInvoker {

    public void initialize();
    
    public void invokeTask();
    
    public TaskExecutor getTaskExecuter();
}
