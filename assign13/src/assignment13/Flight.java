package assignment13;

import java.util.ArrayList;

/**
 * Represents a flight between two airports. Thought of as an edge in the graph.
 * @author Nickolas Lee (U0860626)
 * @author Thomas Osimitz (U0970671)
 *
 */
public class Flight {
	private Airport origin, destination;
	private double cost, time, delay, distance, fractionCanceled;
	private int count;
	private ArrayList<String> carriers;
	
	
	public Flight(Airport origin, Airport destination, double cost, double time, double delay, double distance,
			double fractionCanceled, String carrier) {
		carriers = new ArrayList<String>();
		this.origin = origin;
		this.destination = destination;
		this.cost = cost;
		this.time = time;
		this.delay = delay;
		this.distance = distance;
		this.fractionCanceled = fractionCanceled;
		this.carriers.add(carrier);
		count = 1;
	}

	/**
	 * Aggregates flights from the same origin and destination. 
	 * Updates the current flight object with new information using a running
	 * average. 
	 */
	public void addFlight(double cost, double time, double delay, double distance,
			double fractionCanceled, String carrier){
		// TODO add if check for negative values
		this.cost = rollingAverage(this.cost, cost, count);
		this.time = rollingAverage(this.time, time, count);
		this.delay = rollingAverage(this.delay, delay, count);
		this.distance = rollingAverage(this.distance, distance, count);
		this.fractionCanceled = rollingAverage(this.fractionCanceled, fractionCanceled, count);
		if(!this.carriers.contains(carrier)) {
			this.carriers.add(carrier);
		}
		count++;
	}
	
	private double rollingAverage(double value, double newDatum, int count) {
		return ((value*(count) + newDatum)/(count + 1));
	}
	
	public boolean isCarrier(String carrier){
		return this.carriers.contains(carrier);
	}

	/**
	 * @return the origin
	 */
	public Airport getOrigin() {
		return origin;
	}

	/**
	 * @return the destination
	 */
	public Airport getDestination() {
		return destination;
	}

	/**
	 * @return the Weight of this flight
	 */
	public double getWeight(FlightCriteria fc) {
		switch(fc){
		case COST: return cost;
		case DELAY: return delay;
		case DISTANCE: return distance;
		case CANCELED: return fractionCanceled;
		case TIME: return time;
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Flight [origin=" + origin.getName() + ", destination=" + destination.getName() + ", cost=" + cost + ", time=" + time
				+ ", delay=" + delay + ", distance=" + distance + ", fractionCanceled=" + fractionCanceled + ", count="
				+ count + ", carriers=" + carriers.toString() + "]";
	}
	
	
}
