package labb6;

public class CarLeaveEvent extends Event {

	final private int id;
	final private CarWashState.WashType type;
	
	public CarLeaveEvent(double time, int id, CarWashState.WashType type) {
		super(time);
		this.id = id;
		this.type = type;
	}

	@Override
	public void runEvent(SimState state) {
		// TODO Auto-generated method stub
	
		if (state instanceof CarWashState washState) {
			washState.addFree(type, getTime());
		}
	}

	@Override
	public String toString() {
		return "Leave";
	}

}
