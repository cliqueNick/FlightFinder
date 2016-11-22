package assign13;

/**
 * An edge in the graph
 * @author Nickolas Lee
 *
 */
public class Flight {
	private Airport origin, destination;
	private double cost, time, delay, distance, fractionCanceled;
	private int count;
	//enum carrier
	
	
	public Flight(Airport origin, Airport destination, double cost, double time, double delay, double distance,
			double fractionCanceled) {
		this.origin = origin;
		this.destination = destination;
		this.cost = cost;
		this.time = time;
		this.delay = delay;
		this.distance = distance;
		this.fractionCanceled = fractionCanceled;
		count = 1;
	}

	/**
	 * Aggregates flights from the same origin and destination
	 */
	public void addFlight(double cost, double time, double delay, double distance,
			double fractionCanceled){
		this.cost = rollingAverage(this.cost, cost, count);
		this.time = rollingAverage(this.time, time, count);
		this.delay = rollingAverage(this.delay, delay, count);
		this.distance = rollingAverage(this.distance, distance, count);
		count++;
	}
	
	private double rollingAverage(double value, double newDatum, int count) {
		return ((value*(count) + newDatum)/(count + 1));
	}
	
	public boolean isCarrier(String carrier){
//		for(each)
		return false;
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
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @return the delay
	 */
	public double getDelay() {
		return delay;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @return the fractionCanceled
	 */
	public double getFractionCanceled() {
		return fractionCanceled;
	}
	
	
}
