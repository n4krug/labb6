package labb6;

import java.util.Observable;

public class SimState extends Observable {

	private double currentTime = 0.0;
	private boolean isRunning = false;
	final private EventQueue events;

	public SimState(EventQueue queue, double maxRunTime) {
		events = queue;
		events.add(new StartEvent(0.0));
		events.add(new StopEvent(maxRunTime));
	}
	
	public EventQueue getEventQueue() {
		return events;
	}
	
	public void setIsRunning(boolean state) {
		isRunning = state;
	}
	
	public double getTime() {
		return currentTime;
	}
	
	public boolean getIsRunning() {
		return isRunning;
	}
	
	public void eventComplete(double time) {
		this.currentTime = time;
		this.setChanged();
		this.notifyObservers();
	}
	
}
