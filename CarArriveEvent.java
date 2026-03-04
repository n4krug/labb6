package labb6;

public class CarArriveEvent extends Event {

	final private int id;
	
	public CarArriveEvent(double time, int id) {
		super(time);
		this.id = id;
	}

	@Override
	public void run(SimState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "Arrive";
	}

}
