package controller;

import java.util.EventListener;

public interface TimerListener extends EventListener{
  public void timerTick(String currentTime, boolean inTime);
  public void timerReset(String resetTime);
  public void timerSetMaximumTime(String maxTime);
  public void timerOverrun();
}
