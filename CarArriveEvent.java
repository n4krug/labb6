package labb6;

/**
 * Event triggered when a car arrives to the carwash
 */
public class CarArriveEvent extends CarEvent {

	public CarArriveEvent(double time, int id) {
		super(time, id);
	}

	@Override
	public void runEvent(SimState state) {
		if (state instanceof CarWashState washState) {
			washState.carArrived(getId(), getTime());
		}
	}

	@Override
	public String toString() {
		return "Arrive";
	}
}
