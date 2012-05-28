package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import model.TaskScheduler;
import model.Logging;
import view.MainGUI;

public class TimeKeeper{
	public int timeCounterInSeconds = 0;
	public int taskExecutionTimeInSeconds;
	private double totalTeamTimeInMinutes;
	private static double configurationTimeInMinutes;
	private static double runTimeInMinutes;
	private double maximumTimeInMinutes = 0.0;
	public static double elapsedTimeInMinutes = 0.0;
	public double remainingTimeInMinutes;
	private TaskScheduler taskscheduler;
	public MainGUI mainGui;
	private Logging logg;
	private String timerLogID= "Timer";
	
	private static TimeKeeper instance = null;
	   protected TimeKeeper(MainGUI mG) {
		  logg = Logging.getInstance();
		  mainGui = mG;
	      // Exists only to defeat instantiation.
	   }
	   
	   
	   public static TimeKeeper getInstance(MainGUI mG) {
	      if(instance == null) {
	         instance = new TimeKeeper(mG);
	      }
	      return instance;
	   }
	   
	   public static TimeKeeper getInstance() {
		      if(instance == null) {
		         System.out.println("error");
		      }
		      return instance;
		   }
	 
	public Timer MasterTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e)
        {
        	timeCounterInSeconds++;
        	//System.out.println(timeCounterInSeconds);
        	int minutes = (int) timeCounterInSeconds / 60;
        	int seconds = (int) (timeCounterInSeconds) % 60;
        	
        	System.out.println( (minutes<10? "0" + minutes : minutes) + ":" + (seconds<10? "0" + seconds : seconds) );
        	if(mainGui != null) 
        		mainGui.setTimerLabelText( (minutes<10? "0" + minutes : minutes) + ":" + (seconds<10? "0" + seconds : seconds) );
        	if(timeCounterInSeconds >= (maximumTimeInMinutes * 60)){
        		MasterTimer.stop();
        		timeCounterInSeconds = 0;
        		taskscheduler = TaskScheduler.getInstance();
        		try {
        			taskscheduler.taskServer.listenForConnection();
        		}
        		catch(Exception ex) {
        			System.out.println("Exception in TimeKeeper actionPerformed (for MasterTimer): " + ex.getMessage());
        		}
        	}
        }
    });
	   
	public void startTimer() {
		System.out.println("00:00");
		MasterTimer.start();
		if(mainGui != null) 
			mainGui.setTimerLabelText("00:00");
		logg.LoggingFile(timerLogID,"Started");
	}
	
	public void stopTimer() {
		taskscheduler = TaskScheduler.getInstance();
		MasterTimer.stop();
		logg.LoggingFile(timerLogID,"Stopped in " + timeCounterInSeconds + "sec");
		
		totalTeamTimeInMinutes = getTimer() / 60;
		taskscheduler.timer.cancel();
		System.out.println("Execution Time for " + taskscheduler.taskServer.getTeamName() + " is " + totalTeamTimeInMinutes);
		taskscheduler.taskServer.listenForConnection();
		
		timeCounterInSeconds = 0;
	}
	
	public int getTimer() {
		return timeCounterInSeconds;
	}
	
	public double getConfigurationTimeInMinutes() {
		return configurationTimeInMinutes;
	}
	
	public double getRunTimeInMinutes() {
		return runTimeInMinutes;
	}
	
	public double getMaximumTimeInMinutes() {
		return maximumTimeInMinutes;
	}
	
	public double getElapsedTimeInMinutes() {
		return elapsedTimeInMinutes;
	}
	
	public double getTotalTeamTimeInMinutes() {
		return totalTeamTimeInMinutes;
	}
	
	public void setElapsedTimeInMinutes(double d) {
		elapsedTimeInMinutes = d;
	}
	
	public void setConfigurationTimeInMinutes(double d) {
		configurationTimeInMinutes = d;
	}
	
	public void setRunTimeInMinutes(double d) {
		runTimeInMinutes = d;
	}
	
	public void setMaximumTimeInMinutes() {
		maximumTimeInMinutes = configurationTimeInMinutes + runTimeInMinutes;
		System.out.println("maxInMin: " + maximumTimeInMinutes);
	}	
	
	public void setTotalTeamTimeInMinutes(double d) {
		totalTeamTimeInMinutes = d;
	}
	
	
}
