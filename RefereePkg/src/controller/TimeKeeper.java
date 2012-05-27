package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import model.TaskServer;
import model.Logging;
import view.MainGUI;

public class TimeKeeper{
	public int timeCounterInSeconds = 0;
	public int taskExecutionTimeInSeconds;
	public int totalTeamTimeInMinutes;
	public double configurationTimeInMinutes = 2.0;
	public double runTimeInMinutes = 5.0;
	public double maximumTimeInMinutes = configurationTimeInMinutes + runTimeInMinutes;
	public double elapsedTimeInMinutes;
	public double remainingTimeInMinutes;
	private TaskServer tServer;
<<<<<<< HEAD
	private Logging logg = Logging.getInstance("TaskLog.log");
	private String timerOp= "Timer";
	private static MainGUI mainGui = null;
=======
	private Logging logg;
	private String timerLogID= "Timer";
>>>>>>> 22992518be7d133e8c2681c8a51adc10d5ca5ce2
	
	private static TimeKeeper instance = null;
	   protected TimeKeeper() {
		  logg = Logging.getInstance();
	      // Exists only to defeat instantiation.
	   }
	   public static TimeKeeper getInstance(MainGUI mG) {
	      if(instance == null) {
	         instance = new TimeKeeper();
	      }
	      mainGui = mG;
	      return instance;
	   }
	   
	   public static TimeKeeper getInstance() {
		   if(instance == null) {
			   instance = new TimeKeeper();
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
        	mainGui.setTimerLabelText( (minutes<10? "0" + minutes : minutes) + ":" + (seconds<10? "0" + seconds : seconds) );
        	if(timeCounterInSeconds >= (maximumTimeInMinutes * 60)){
        		MasterTimer.stop();
        		timeCounterInSeconds = 0;
        		tServer.listenForConnection();
        	}
        }
    });

	   
	public void startTimer(){
		System.out.println(timeCounterInSeconds);
		MasterTimer.start();
<<<<<<< HEAD
		mainGui.setTimerLabelText("00:00");
		logg.LoggingFile(timerOp,"Started");
=======
		logg.LoggingFile(timerLogID,"Started");
>>>>>>> 22992518be7d133e8c2681c8a51adc10d5ca5ce2
	}
	
	public void stopTimer(){
		System.out.println(timeCounterInSeconds);
		MasterTimer.stop();
		timeCounterInSeconds = 0;
		logg.LoggingFile(timerLogID,"Stopped in " + timeCounterInSeconds + "sec");
	}
	
	public int getTimer(){
		System.out.println(timeCounterInSeconds);
		return timeCounterInSeconds;
	}
}
