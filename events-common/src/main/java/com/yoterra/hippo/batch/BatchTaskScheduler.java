package com.yoterra.hippo.batch;

import org.springframework.scheduling.annotation.Scheduled;

public abstract class BatchTaskScheduler 
	//extends ScheduledAnnotationBeanPostProcessor 
	{
	
	
	private final int conditialRunIterationLimit;
	private volatile int idleCount = 0;
	private volatile int newTasksCount = 0;
	private volatile boolean runningNow = false;
	
	public BatchTaskScheduler(int cril){
		this.conditialRunIterationLimit = cril;
	}
	
	protected void notifyNewTask(){
		notifyNewTasks(1);
	}
	
	protected void notifyNewTasks(int n){
		this.newTasksCount += n;
	}
	
	/**
	 * Process the tasks and return the number of processed tasks
	 * It is not necessary to process all the new tasks
	 * @return
	 */
	protected abstract int process();		
	
	protected void schedule(){
		
		// don't run if it's running at the moment,
		// don't run if no new tasks unless the idle iterations limit is exceeded
		if(!runningNow && (newTasksCount > 0 || idleCount >= conditialRunIterationLimit)) {				// just optimizaiton			
			runningNow = true;
			try {
				int processedTasks = process();
				newTasksCount = Math.max(0, newTasksCount - processedTasks);
			}finally {
				runningNow = false;
				idleCount = 0;
			}
		}else {
			idleCount++;			// number of skipped iterations, cause there is probably nothing to do
		}
	}
	
	@Scheduled(initialDelay = 60*1000, fixedDelay = 30*1000)	// the default, run every 30sec with 1 min initial delay 
	public void annotatedScheduler(){
		schedule();
	}

}
