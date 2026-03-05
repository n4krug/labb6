package labb6;

import java.util.Formatter;
import java.util.Observable;
import java.util.Observer;

public class SimView implements Observer{
	
	private SimState simState;
	private StringBuilder sb;
	private Formatter formatter;
	
	public SimView(SimState simState) {
		
		this.simState = simState;
		
		this.simState.addObserver(this);
		
		sb = new StringBuilder("");
		formatter = new Formatter(sb);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		Event event = simState.getEventQueue().peek();
		
		sb.replace(0, sb.length(), "");
		
		if(event instanceof StartEvent) {
			sb.append("----------------------------------------\nTime   Event\n");
		}
		
		formatter.format("%f   %s", event.getTime(), event.toString());
		
		if(event instanceof StopEvent) {
			sb.append("\n---------------------------------");
		}
		
		System.out.println(sb.toString());
		
	}

	
}
