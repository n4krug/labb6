package labb6.Generic;

import labb6.Generic.event.Event;

public class Simulator {

	private final SimState state;
	
	/**
	 * Initiates simulator with state and view. Also binds view as a observer to state
	 * 
	 * @param state	Simulation {@link SimState}
	 * @param view	Simulation {@link SimView}
	 */
	public Simulator(SimState state, SimView view) {
		this.state = state;
		
		state.addObserver(view);
	}
	
	/**
	 * Run the simulation. Loops through events and runs them in order of execution time.
	 */
	public void run() {
		
		final EventQueue queue = state.getEventQueue();
		
		while (!queue.isEmpty() && state.getIsRunning()) {
			Event event = queue.peek();
			event.run(state);
			queue.poll();
		}
	}
	
	/**
	 * Test main, runs a generic empty simulation with only start and stop event
	 * @param args
	 */
	public static void main(String[] args) {
		
		EventQueue queue = new EventQueue();
		
		SimState state = new SimState(queue, 15.0);
	
		SimView view = new SimView();
		
		Simulator test = new Simulator(state, view);
		
		test.run();
	}
}
