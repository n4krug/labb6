package labb6;

public class Simulator {

	private final SimState state;
	private final SimView view;
	
	public Simulator(SimState state, SimView view) {
		this.state = state;
		this.view = view;
		
//		state.addObserver(view);
	}
	
	public void run() {
		
		final EventQueue queue = state.getEventQueue();
		
		state.eventComplete(0);
		
		while (!queue.isEmpty() && state.getIsRunning()) {
			Event event = queue.poll();
			event.run(state);
		}
	}
	
	public static void main(String[] args) {
		
		EventQueue queue = new EventQueue();
		
		SimState state = new SimState(queue, 15.0);
	
		SimView view = new SimView(state);
		
		Simulator test = new Simulator(state, view);
		
		test.run();
	}
}
