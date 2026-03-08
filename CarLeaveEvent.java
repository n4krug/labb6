package labb6;

/**
 * Event run when 
 */
public class CarLeaveEvent extends CarEvent {

	final private CarWashState.WashType type;
	
	public CarLeaveEvent(double time, int id, CarWashState.WashType type) {
		super(time, id);
		this.type = type;
	}

	@Override
	public void runEvent(SimState state) {
		if (state instanceof CarWashState washState) {
			washState.addFree(type, getTime());
		}
	}

	@Override
	public String toString() {
		return "Leave";
	}
}
