package assignment13;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

/**
 * Elaborate tests the NetworkGraph class
 * @author Nickolas Lee (U0860626)
 * @author Thomas Osimitz (U0970671)
 *
 */
public class NetworkGraphTest {
	NetworkGraph fiveUniqueRoutes, tenDuplicateFlights, fiftyFiftyFlight, fiveModified, forCanceled, forAverage;
	String loc = "src/assignment13/";

	@Before
	public void setUp() throws Exception {
		fiveUniqueRoutes = new NetworkGraph(loc+"fiveOfBoth.csv");
		tenDuplicateFlights = new NetworkGraph(loc+"tenDuplicateFlights.csv");
		fiftyFiftyFlight = new NetworkGraph(loc+"Flight50_50.csv");
		fiveModified = new NetworkGraph(loc+"Flight5_5.csv");
		forCanceled = new NetworkGraph(loc+"FlightModifiedForCanceledTest.csv");
		forAverage = new NetworkGraph(loc+"forAverage.csv");
	}

	@Test
	public void testFiveUniqueRoutes() {
		assertEquals(5, fiveUniqueRoutes.airportSet.size());
		assertTrue(fiveUniqueRoutes.airportSet.containsKey("MZF"));
	}

	@Test
	public void testTenDuplicateRoutes() {
		String actual = tenDuplicateFlights.airportSet.get("MZF").getRoute("AHH").toString();
		String expected = "Flight [origin=MZF, destination=AHH, cost=87.5, time=400.0, delay=50.5, "
				+ "distance=37.5, fractionCanceled=0.5, count=2, carriers=[AA, OO]]";
		assertEquals(5, tenDuplicateFlights.airportSet.size());
		tenDuplicateFlights.generateDotFile(loc+"tenFlightDot.dot", FlightCriteria.COST);
		assertEquals(expected, actual);
	}

	@Test
	public void testDOT() throws FileNotFoundException{
		String input = loc+"Flight5_5";
		NetworkGraph newFlight = new NetworkGraph(input + ".csv");
		newFlight.generateDotFile(input + ".dot", FlightCriteria.COST);
	}

	@Test
	public void testGetBestPathNoAirlineDistance(){
		String expected = "Path Length: 3594.0\nPath: [ZKA, OIW, OSM, SXO]";
		BestPath experimental = fiftyFiftyFlight.getBestPath("ZKA", "SXO", FlightCriteria.DISTANCE);
		fiftyFiftyFlight.generateDotFile(loc+"fiftyFiftyDISTANCE.dot", FlightCriteria.DISTANCE);
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testGetBestPathNoAirlineCost(){
		String expected = "Path Length: 1588.44\nPath: [ZKA, KIT, PDE, SXO]";
		BestPath experimental = fiftyFiftyFlight.getBestPath("ZKA", "SXO", FlightCriteria.COST);
		assertEquals(expected, experimental.toString());
		fiftyFiftyFlight.generateDotFile(loc+"fiftyFiftyFlightCOST.dot", FlightCriteria.COST);
	}

	@Test
	public void testDijkstrasOperational(){
		BestPath experimental = fiveModified.getBestPath("QKJ", "JKP", FlightCriteria.COST);
		String expected = "Path Length: 937.81\nPath: [QKJ, HJF, GCK, JKP]";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testOriginAirportDoesNotExist() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("FBI", "JKP", FlightCriteria.COST);
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testDesitinationAirportDoesNotExist() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("JKP", "CIA", FlightCriteria.COST);
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testInvalidAirportName() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("San Diego", "CIA", FlightCriteria.COST);
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testEmptyAirportName() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("", "JKP", FlightCriteria.COST);
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testBothExistButNoPathBetweenThem() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("IMV", "LXR", FlightCriteria.COST);
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testAirportWithoutAnyDepartures() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("COF", "LXR", FlightCriteria.COST);
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testVisitedVariable() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("IKV", "GLU", FlightCriteria.COST);
		String expected = "Path Length: 0.0\nPath: []";
		fiftyFiftyFlight.generateDotFile(loc + "fiftyFiftyLoop.dot", FlightCriteria.COST);
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testOriginEqualsDestination() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("IKV", "IKV", FlightCriteria.COST);
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testTIMEEnum() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("ZKA", "SXO", FlightCriteria.TIME);
		String expected = "Path Length: 520.0\nPath: [ZKA, OIW, OSM, SXO]";
		fiftyFiftyFlight.generateDotFile(loc+"fiftyFiftyTIME.dot", FlightCriteria.TIME);
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testCANCELEDEnum() {
		BestPath experimental = forCanceled.getBestPath("ZKA", "SXO", FlightCriteria.CANCELED);
		String expected = "Path Length: 1.22\nPath: [ZKA, KIT, PDE, SXO]";
		fiftyFiftyFlight.generateDotFile(loc+"fiftyFiftyCANCELED.dot", FlightCriteria.CANCELED);
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testDELAYEnum() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("ZKA", "SXO", FlightCriteria.DELAY);
		String expected = "Path Length: 761.0\nPath: [ZKA, OIW, OSM, SXO]";
		fiftyFiftyFlight.generateDotFile(loc+"fiftyFiftyDELAY.dot", FlightCriteria.DELAY);
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testInvalidAirliner() {
		BestPath experimental = fiftyFiftyFlight.getBestPath("ZKA", "SXO", FlightCriteria.COST, "NSA");
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	@Test
	public void testCleanu(){
		BestPath expected = fiftyFiftyFlight.getBestPath("ZKA", "SXO", FlightCriteria.DISTANCE);
		BestPath actual = fiftyFiftyFlight.getBestPath("ZKA", "SXO", FlightCriteria.DISTANCE);
		assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void testAveraginCancelled() {
		Flight flight = forAverage.airportSet.get("HJF").getDepartFlights().getFirst();
		assertEquals(36.54, flight.getWeight(FlightCriteria.CANCELED), 0);
	}

	@Test
	public void testAveraginDistance() {
		Flight flight = forAverage.airportSet.get("HJF").getDepartFlights().getFirst();
		assertEquals(36.54, flight.getWeight(FlightCriteria.DISTANCE), 0);
	}

	@Test
	public void testAveraginTime() {
		Flight flight = forAverage.airportSet.get("HJF").getDepartFlights().getFirst();
		assertEquals(-1, flight.getWeight(FlightCriteria.TIME), 0);
	}

	@Test
	public void testAverageDelay() {
		Flight flight = forAverage.airportSet.get("HJF").getDepartFlights().getFirst();
		assertEquals(36.54, flight.getWeight(FlightCriteria.DELAY), 0);
	}

	@Test
	public void testAveraginCost() {
		Flight flight = forAverage.airportSet.get("HJF").getDepartFlights().getFirst();
		assertEquals(36.54, flight.getWeight(FlightCriteria.COST), 0);
	}

	@Test
	public void testDijkstrasIgnoresNegativeOne() {
		BestPath experimental = forAverage.getBestPath("HJF", "GCK", FlightCriteria.TIME);
		String expected = "Path Length: 0.0\nPath: []";
		assertEquals(expected, experimental.toString());
	}

	// Ways to improve
	// Route cancellation odds, as well as if flight has always been cancelled, skip!
	// return averages per airliner not per flight origin and destination
}
