package labb6;

public abstract class Event implements Comparable<Event> {

	final private double time;

	public Event(double time) {
		this.time = time;
	}

	public void run(SimState state) {
		state.eventStarted(time);
		
		runEvent(state);
		
		state.eventComplete(time);
	}
	
	abstract protected void runEvent(SimState state);

	@Override
	public int compareTo(Event arg0) {
		return Double.compare(this.getTime(), arg0.getTime());
	}

	public double getTime() {
		return time;
	}

	public abstract String toString();

}
