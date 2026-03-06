package labb6;

public class StartEvent extends Event {

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
