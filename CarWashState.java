package labb6;

import java.util.LinkedList;

public class CarWashState extends SimState {

	private int fastFree, slowFree;
	private int rejected = 0;
	private final int maxQueueSize;
	private final UniformRandomStream fastRandom, slowRandom;
	private double idleTime = 0;
	private double queueTime = 0;
	private final LinkedList<Integer> carQueue = new LinkedList<>();

	public CarWashState(EventQueue queue, CarFactory factory, int numFast, int numSlow, int maxQueueSize,
			UniformRandomStream fastWashTime, UniformRandomStream slowWashTime, double maxRunTime) {
		super(queue, maxRunTime);
		
		fastFree = numFast;
		slowFree = numSlow;

		this.maxQueueSize = maxQueueSize;
		fastRandom = fastWashTime;
		slowRandom = slowWashTime;

		factory.spawnCars(queue);
	}

	/**
	 * Updates idle and queue time and triggers super.eventComplete(double)
	 */
	public void eventStarted(double time) {
		if (getCarQueueSize() > 0) {
			addQueueTime((time - getTime()) * getCarQueueSize() );
		}

		if (fastFree > 0 || slowFree > 0) {
			addIdleTime((time - getTime()) * (getFastFree() + getSlowFree()));
		}
		
		super.eventStarted(time);
	}

	private double getFastTime() {
		return fastRandom.next();
	}

	private double getSlowTime() {
		return slowRandom.next();
	}

	/**
	 * Creates a leave event for next car in queue if space exists in a washer
	 * 
	 * @return false if washers where full
	 */
	private boolean enterWasher(double time) {

		double leaveTime;
		WashType type;

		if (getFastFree() > 0) {
			leaveTime = getFastTime();
			type = WashType.FAST;
		} else if (getSlowFree() > 0) {
			leaveTime = getSlowTime();
			type = WashType.SLOW;
		} else {
			return false;
		}
		
		leaveTime += time;

		int id = carQueue.poll();
		reduceFree(type);

		CarLeaveEvent leave = new CarLeaveEvent(leaveTime, id, type);
		getEventQueue().add(leave);
		return true;
	}

	/**
	 * Washes a car if there's any free washers else adds a car with id to carQueue
	 * if there is space. Triggered by {@link CarArriveEvent}.
	 * 
	 * @param id The cars id
	 */
	public void carArrived(int id, double time) {
		if (getCarQueueSize() >= maxQueueSize) {
			addRejected();
			return;
		}
		carQueue.add(id);
		
		enterWasher(time); // Try putting first car in washer (does nothing if no free washer)
	}

	/**
	 * Triggered by CarLeaveEvent to mark wash as free for next car. Starts washing
	 * car if one is cueing.
	 * 
	 * @param type Type of the wash (FAST/SLOW)
	 */
	public void addFree(WashType type, double time) {
		if (type == WashType.FAST) {
			fastFree++;
		} else if (type == WashType.SLOW) {
			slowFree++;
		}

		if (carQueue.size() > 0) {
			enterWasher(time);
		}
	}

	private void reduceFree(WashType type) {
		if (type == WashType.FAST) {
			fastFree--;
		} else if (type == WashType.SLOW) {
			slowFree--;
		}
	}

	private void addIdleTime(double time) {
		idleTime += time;
	}

	private void addQueueTime(double time) {
		queueTime += time;
	}

	private void addRejected() {
		rejected++;
	}

	// Getters
	public int getCarQueueSize() {
		return carQueue.size();
	}

	public int getFastFree() {
		return fastFree;
	}

	public int getSlowFree() {
		return slowFree;
	}

	public double getIdleTime() {
		return idleTime;
	}

	public double getQueueTime() {
		return queueTime;
	}

	public int getRejected() {
		return rejected;
	}
	
	// Test main
	public static void main(String[] args) {
		
		EventQueue queue = new EventQueue();
		
		CarFactory factory = new CarFactory(15, new ExponentialRandomStream(2, 1234));
		
		CarWashState state = new CarWashState(queue, factory, 2, 2, 5, new UniformRandomStream(2.8,4.6,1234), new UniformRandomStream(3.5,6.7,1234), 15.0);
	
		CarWashView view = new CarWashView(state);
		
		Simulator test = new Simulator(state, view);
		
		test.run();
		
		System.out.println(state.getQueueTime());
		System.out.println(state.getIdleTime());
		System.out.println(state.getCarQueueSize());
		System.out.println(state.getRejected());
	}
}
