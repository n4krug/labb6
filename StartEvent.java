package labb6;

public class StartEvent extends Event {

	/**
	 * Creates a simple event that marks the start of simulation
	 * @param time
	 */
	public StartEvent(double time) {
		super(time);
	}

	@Override
	public void runEvent(SimState state) {
		state.setIsRunning(true);
	}

	@Override
	public String toString() {
		return "Start";
	}

}
