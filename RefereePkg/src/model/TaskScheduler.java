package model;

import java.util.Timer;
import java.util.TimerTask;

import controller.TimeKeeper;

public class TaskScheduler extends TimerTask {
	public Timer timer;
	private TimeKeeper timekeeper = TimeKeeper.getInstance();
	private TaskScheduler taskscheduler;
	private TaskServer tServer;
	
	private static TaskScheduler instance = null;
	   protected TaskScheduler() {
	      // Exists only to defeat instantiation.
	   }
	   public static TaskScheduler getInstance() {
	      if(instance == null) {
	         instance = new TaskScheduler();
	      }
	      return instance;
	   }
	   
	public void timeOut(){
		if(timekeeper.getTimer() > (int)(timekeeper.configurationTimeInMinutes*60)){
			timekeeper.elapsedTimeInMinutes = timekeeper.getTimer()/60;
			timekeeper.remainingTimeInMinutes = timekeeper.maximumTimeInMinutes - timekeeper.elapsedTimeInMinutes;
			timer.schedule (taskscheduler,(int)(timekeeper.remainingTimeInMinutes * 60 * 1000));
		}
		else{
			timer.schedule (taskscheduler,(int)(timekeeper.runTimeInMinutes * 60 * 1000));
		}
	}

	@Override
	public void run() {
		tServer.listenForConnection();
	}

}
