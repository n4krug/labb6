package labb6.Generic.event;

import labb6.Generic.SimState;

/**
 * A sortable time based event
 */
public abstract class Event implements Comparable<Event> {

	final private double time;

	/**
	 * Create a event to be run at time
	 * @param time	the time to run event at
	 */
	public Event(double time) {
		this.time = time;
	}

	/**
	 * Triggers state updates and runs the event
	 * @param state	{@link SimState} object to trigger updates on 
	 */
	public void run(SimState state) {
		state.eventStarted(time);
		
		runEvent(state);
		
		state.eventComplete(time);
	}
	
	abstract protected void runEvent(SimState state);

	/**
	 * Allows for sorting based on time
	 */
	@Override
	public int compareTo(Event event2) {
		return Double.compare(this.getTime(), event2.getTime());
	}

	/**
	 * @return the time the event should be run at
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @return the Event name
	 */
	public abstract String toString();

}
