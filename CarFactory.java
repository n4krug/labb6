package labb6;

public class CarFactory {
	
	private double maxTime;
	private ExponentialRandomStream random;
	
	public CarFactory(double maxTime, ExponentialRandomStream random){
		this.maxTime = maxTime;
		this.random = random;
	}
	
	public void spawnCars(EventQueue eventQueue) {
		
		double time = 0 + random.next();
		int id = 0;
		
		while(time < maxTime) {
			eventQueue.add(new CarArriveEvent(time, id));
			
			time += random.next();
			id++;
		}
	}

}
