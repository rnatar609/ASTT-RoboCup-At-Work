package model;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.EventListenerList;

import controller.ConnectionListener;
import controller.TimerListener;

public class TaskTimer implements ConnectionListener {

	private long configTime;
	private long runTime;
	private Timer timer;
	private long startTime;
	private long startRunTime;
	private long currentSec;
	private boolean configTimeRunning;
	private boolean runTimeRunning;
	private boolean timerStop;
	private boolean inTime;
	private Logging logg;
	private String logId = "Timer";

	private EventListenerList listOfTimerListeners = new EventListenerList();

	class Task extends TimerTask {

		@Override
		public void run() {
			if (timerStop) {
				this.cancel();
				return;
			}
			currentSec++;
			if (configTimeRunning) {
				long usedConfigTime = System.currentTimeMillis() - startTime;
				if (usedConfigTime >= configTime) {
					setConfigTimeStop();
					inTime = false;
				}
			}
			if (runTimeRunning) {
				long usedRunTime = System.currentTimeMillis() - startRunTime;
				if (usedRunTime >= runTime) {
					notifyTimerTick(secToString(currentSec), inTime);
					notifyTimerOverrun();
					cancel();
					return;
				}
			}
			notifyTimerTick(secToString(currentSec), inTime);
		}
	}

	public TaskTimer() {
		logg = Logging.getInstance();
		timerStop = true;
	}

	public void startNewTimer(long configTime, long runTime) {

		if (timerStop) {
			this.configTime = configTime * 1000;
			this.runTime = runTime * 1000;
			currentSec = 0;
			timer = new Timer();
			configTimeRunning = true;
			runTimeRunning = false;
			inTime = true;
			timerStop = false;
			startTime = System.currentTimeMillis();
			timer.scheduleAtFixedRate(new Task(), 1000, 1000);
			logg.LoggingFile(logId, "new config time  "
					+ millisecToString(this.configTime));
			logg.LoggingFile(logId, "new run time "
					+ millisecToString(this.runTime));
			logg.LoggingFile(logId, "Configuration time startet at "
					+ millisecToString(currentSec));
			notifyTimerReset(secToString(currentSec));
			notifyTimerSetMaximumTime(("cfg time: ").concat(millisecToString(this.configTime)));
		}
	}

	public void startRunTimer() {
		runTimeRunning = true;
	}

	public void stopTimer() {
		timerStop = true;
		logg.LoggingFile(logId, "stopped at " + secToString(currentSec));
		CompetitionLogging.setStopTime(secToString(currentSec));
	}

	public void resetTimer() {
		this.configTime = 0;
		this.runTime = 0;
		currentSec = 0;
		configTimeRunning = false;
		runTimeRunning = false;
		timerStop = true;
		notifyTimerReset(secToString(currentSec));
	}

	public void setConfigTimeStop() {
		configTimeRunning = false;
		currentSec = 0;
		startRunTime = System.currentTimeMillis();
		runTimeRunning = true;
		notifyTimerSetMaximumTime(("run time: ").concat(millisecToString(runTime)));
		logg.LoggingFile(logId, "Run time started at "
				+ secToString(currentSec));
		CompetitionLogging.setRunTimeStart(secToString(currentSec));
	}

	private void notifyTimerTick(String currentTime, boolean inTime) {
		Object[] listeners = listOfTimerListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TimerListener.class) {
				((TimerListener) listeners[i + 1]).timerTick(currentTime,
						inTime);
			}
		}
	}

	private void notifyTimerReset(String resetTime) {
		Object[] listeners = listOfTimerListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TimerListener.class) {
				((TimerListener) listeners[i + 1]).timerReset(resetTime);
			}
		}
	}

	private void notifyTimerSetMaximumTime(String maxTime) {
		Object[] listeners = listOfTimerListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TimerListener.class) {
				((TimerListener) listeners[i + 1]).timerSetMaximumTime(maxTime);
			}
		}
	}

	public void notifyTimerOverrun() {
		Object[] listeners = listOfTimerListeners.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TimerListener.class) {
				((TimerListener) listeners[i + 1]).timerOverrun();
			}
		}
	}

	public void addTimerListener(TimerListener tL) {
		listOfTimerListeners.add(TimerListener.class, tL);
	}

	public void removeTimerListener(TimerListener tL) {
		listOfTimerListeners.remove(TimerListener.class, tL);
	}

	private String secToString(long sec) {
		return millisecToString(sec * 1000);
	}

	private String millisecToString(long millisec) {
		Date date = new Date(millisec);
		String formattedDate = new SimpleDateFormat("mm:ss").format(date);
		return formattedDate;
	}

	@Override
	public void teamConnected(String teamName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void teamDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskSpecSent() {
		if (configTimeRunning)
			setConfigTimeStop();
		inTime = true;
	}
}
