package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import model.TaskScheduler;
import model.ConfigFile;
import model.Logging;
import view.MainGUI;

public class TimeKeeper{
	public int timeCounterInSeconds = 0;
	public int taskExecutionTimeInSeconds;
	private double totalTeamTimeInMinutes;
	private double configurationTimeInMinutes;
	private double runTimeInMinutes;
	private double maximumTimeInMinutes = 0.0;
	public  double elapsedTimeInMinutes = 0.0;
	public  double remainingTimeInMinutes;
	private TaskScheduler taskscheduler;
	//private ConfigFile cfgFile;
	public MainGUI mainGUI;
	private Logging logg;
	private String timerLogID= "Timer";
	
	private static TimeKeeper instance = null;
	
	protected TimeKeeper() {
		  logg = Logging.getInstance();
	   }
	   
	   
	   public static TimeKeeper getInstance() {
	      if(instance == null) {
	         instance = new TimeKeeper();
	      }
	      return instance;
	   }
	   
	   public void SetMainGUI(MainGUI mG)
	   {
	      mainGUI = mG;
	   }
	 
	   public Timer MasterTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e)
        {
        	timeCounterInSeconds++;
        	//System.out.println(timeCounterInSeconds);
        	int minutes = (int) timeCounterInSeconds / 60;
        	int seconds = (int) (timeCounterInSeconds) % 60;
        	
        	System.out.println( (minutes<10? "0" + minutes : minutes) + ":" + (seconds<10? "0" + seconds : seconds) );
        	if(mainGUI != null) 
        		mainGUI.setTimerLabelText( (minutes<10? "0" + minutes : minutes) + ":" + (seconds<10? "0" + seconds : seconds) );
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
		if(mainGUI != null) 
			mainGUI.setTimerLabelText("00:00");
		logg.LoggingFile(timerLogID,"Started");
	}
	
	public void stopTimer() {
		taskscheduler = TaskScheduler.getInstance();
		MasterTimer.stop();
		logg.LoggingFile(timerLogID,"Stopped in " + timeCounterInSeconds + "sec");
		totalTeamTimeInMinutes = getTimer() / 60;
		taskscheduler.timer.cancel();
		System.out.println("TimeKeeper: Execution Time for " + taskscheduler.taskServer.getTeamName() + " is " + totalTeamTimeInMinutes);
		logg.LoggingFile(timerLogID, "Execution Time for " + taskscheduler.taskServer.getTeamName() + "is" + getTotalTeamTimeInMinutes());
		taskscheduler.taskServer.listenForConnection();
		timeCounterInSeconds = 0;
		taskscheduler.newTimer();
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
	
	public double getRemainingTimeInMinutes(){
	    return remainingTimeInMinutes;	
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
	
	public void setConfig(ConfigFile cfgFile) throws Exception{
		setConfigurationTimeInMinutes(cfgFile.getConfigurationTime());
		setRunTimeInMinutes(cfgFile.getRunTime());
		setMaximumTimeInMinutes();
		double maximumTimeInSeconds = getMaximumTimeInMinutes() *60.0;
		int minutes = (int)getMaximumTimeInMinutes();
		int seconds = (int)maximumTimeInSeconds % 60;
		String min = (minutes <10?"0" + minutes: "" + minutes);
		String sec = (seconds <10?"0" + seconds: "" + seconds);
		mainGUI.setMaxTimeLabelText("[max " + min + ":" + sec + "]");
	}
}
