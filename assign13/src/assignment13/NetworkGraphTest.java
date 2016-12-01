package assignment13;

import static org.junit.Assert.*;

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
	NetworkGraph fiveUniqueRoutes;
	NetworkGraph tenDuplicateFlights, fiftyFiftyFlight;
	@Before
	public void setUp() throws Exception {
		fiveUniqueRoutes = new NetworkGraph("fiveOfBoth.csv");
		tenDuplicateFlights = new NetworkGraph("tenDuplicateFlights.csv");
		fiftyFiftyFlight = new NetworkGraph("Flight50_50.csv");
	}

	@Test
	public void testFiveUniqueRoutes() {
		assertEquals(5, fiveUniqueRoutes.airportSet.size());
		assertTrue(fiveUniqueRoutes.airportSet.containsKey("MZF"));
	}
	
	@Test
	public void testTenDuplicateRoutes() {
		String actual = tenDuplicateFlights.airportSet.get("MZF").getRoute("AHH").toString();
		String expected = "Flight [origin=MZF, destination=AHH, cost=87.5, time=400.0, delay=50.5, distance=37.5, fractionCanceled=0.5, count=2, carriers=[AA, OO]]";
		assertEquals(5, tenDuplicateFlights.airportSet.size());
		tenDuplicateFlights.generateDotFile("tenFlightDot.dot");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDOT() throws FileNotFoundException{
		String input = "Flight50_50";
		NetworkGraph newFlight = new NetworkGraph(input + ".csv");
		newFlight.generateDotFile(input + ".dot");
	}
	
	@Test
	public void testGetBestPathNoAirlineDistance(){
		BestPath experimental = fiftyFiftyFlight.getBestPath("DWG", "DOJ", FlightCriteria.DISTANCE);
		System.out.println(experimental.toString());
	}
	
	@Test
	public void testGetBestPathNoAirlineCost(){
		BestPath experimental = fiftyFiftyFlight.getBestPath("DWG", "DOJ", FlightCriteria.COST);
		System.out.println(experimental.toString());
	}
	
	//null
	// origin airport not exist
	// destination airport does not exist
	// both exist but no path between them
	// invalid airport name
	// airport without any departures
	// circularity, infinite loops
	// origin == destination
	// test with coverage
	// invalid enum
	// invalid carrier
	// carrier that does not exist
	// carrier that does not fly out from the origin
	// test cleanup
	// test averaging
	// test negative in input file
	// test each enum
	// javadocs
}
