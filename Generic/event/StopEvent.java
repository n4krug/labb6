package labb6.Generic.event;

import labb6.Generic.SimState;

public class StopEvent extends Event {

	/**
	 * Creates a simple event that stops the simulation
	 * @param time
	 */
	public StopEvent(double time) {
		super(time);
	}

	@Override
	public void runEvent(SimState state) {
		state.setIsRunning(false);
	}

	@Override
	public String toString() {
		return "Stop";
	}

}
