package labb6;

import java.util.Observable;

public class SimState extends Observable {

	private double currentTime = 0.0;
	private boolean isRunning = true;
	final private EventQueue events;

	/**
	 * Initiates state and creates {@link StartEvent} and {@link StopEvent}.
	 * 
	 * @param queue			{@link EventQueue} for the simulation
	 * @param maxRunTime	time to stop simulation at (creates {@link StopEvent})
	 */
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

	/**
	 * Update current time
	 * 
	 * @param time
	 */
	public void eventComplete(double time) {
		this.currentTime = time;
	}

	/**
	 * Trigger observers ({@link SimView})
	 * @param time
	 */
	public void eventStarted(double time) {
		this.setChanged();
		this.notifyObservers();
	}

}
