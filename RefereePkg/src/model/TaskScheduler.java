package model;

import java.util.Timer;
import java.util.TimerTask;

import controller.TimeKeeper;



public class TaskScheduler extends TimerTask {
	public Timer timer;
	private TimeKeeper timekeeper; 
	//private TaskScheduler taskscheduler;
	public TaskServer taskServer;
	
	private static TaskScheduler instance = null;
	   protected TaskScheduler(TaskServer tServer) {
	      // Exists only to defeat instantiation.
		  taskServer = tServer; 
	   }
	   public static TaskScheduler getInstance(TaskServer tServer) {
	      if(instance == null) {
	         instance = new TaskScheduler(tServer);
	      }
	      return instance;
	   }
	   
	   public static TaskScheduler getInstance() {
		   if(instance == null) {
			   System.out.println("Error in TaskScheduler getInstance() no instance given");
		   }
		   return instance;
	   }
	   
	public void timeOut(){
		timekeeper = TimeKeeper.getInstance();
		timer = new Timer();
		
		if(timer == null){System.out.println("timer is null");}
		if(instance == null){System.out.println("taskscheduler is null");}
		if(taskServer == null){System.out.println("taskServer is null");}
				
		if(timekeeper.getTimer() > (int)(timekeeper.getConfigurationTimeInMinutes()*60)){
			timekeeper.setElapsedTimeInMinutes(timekeeper.getTimer()/60);
			timekeeper.remainingTimeInMinutes = timekeeper.getMaximumTimeInMinutes() - timekeeper.getElapsedTimeInMinutes();
			//timer.schedule (taskscheduler,(int)(timekeeper.remainingTimeInMinutes * 60 * 1000));
			timer.schedule(instance, (int)((timekeeper.remainingTimeInMinutes * 60 * 1000)));
		}
		else{
			timer.schedule (instance,(int)(timekeeper.getRunTimeInMinutes() * 60 * 1000));
		}
	}

	@Override
	public void run() {
		taskServer.taskComplete();
	}

}
