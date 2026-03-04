package labb6;

public class StopEvent extends Event {

	public StopEvent(double time) {
		super(time);
	}

	@Override
	public void run(SimState state) {
		// TODO Auto-generated method stub

		

		// Last thing to run
		state.eventComplete(this.getTime());
	}

	@Override
	public String toString() {
		return "Stop";
	}

}
