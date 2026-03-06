package labb6;

public class CarArriveEvent extends Event {

	final private int id;
	
	public CarArriveEvent(double time, int id) {
		super(time);
		this.id = id;
	}

	@Override
	public void runEvent(SimState state) {
		if (state instanceof CarWashState washState) {
			washState.carArrived(id, getTime());
		}
	}

	@Override
	public String toString() {
		return "Arrive";
	}

}
