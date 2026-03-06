package labb6;

public class StartEvent extends Event {

	public StartEvent(double time) {
		super(time);
	}

	@Override
	public void run(SimState state) {
		// TODO Auto-generated method stub

		state.setIsRunning(true);
		// Last thing to run
		state.eventComplete(this.getTime());
	}

	@Override
	public String toString() {
		return "Start";
	}

}
