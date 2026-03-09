package labb6.Generic;

import java.util.PriorityQueue;

import labb6.Generic.event.Event;

@SuppressWarnings("serial")
public class EventQueue extends PriorityQueue<Event> {

	public String toString() {
		String str = "[";
		
		for (Event event : this) {
			str += "(" + event.getTime() + ": " + event + "), ";
		}
		str = str.substring(0, str.length() - 2) + "]";
		
		return str;
	}
}
