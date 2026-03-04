package labb6;


/**
 * Simple class for testing stuff during development
 */
public class Test {
	
	public static void main(String[] args) {
		final EventQueue events = new EventQueue();

		StartEvent start = new StartEvent(0.0);
		events.add(start);
		
		StopEvent stop = new StopEvent(15.0);
		events.add(stop);
		
		CarArriveEvent arrive1 = new CarArriveEvent(10.0, 0);
		events.add(arrive1);
		
		System.out.println(events.toString());
		
		while (events.size() > 0) {
			Event event = events.poll();
			System.out.println("(" + event + ", " + event.getTime() + ")");
		}
	}

}
