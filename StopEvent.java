package labb6;

public class StopEvent extends Event {

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
