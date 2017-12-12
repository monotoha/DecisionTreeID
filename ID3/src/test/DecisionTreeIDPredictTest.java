package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import model.DecisionTreeID;

public class DecisionTreeIDPredictTest {

	@Test(expected = Exception.class)
	public void testPredictRaisesAnExceptionShouldSizeWereInferiorThanTableSizeMinusOne() {
		String[] comicWithoutLong = {"Mayor 160 lbs","Menor 40"};
		String target = "H";
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.readCSV("csv-files/simpsons.cvs");
			assertEquals(dt.prediction(comicWithoutLong),target);
		} catch (FileNotFoundException e) {
			fail("simpsons.cvs not found.");
		}
	}
	@Test
	public void testPredictShouldPredictIgnoringLastColumnIfSizeWereEqualsThanTableSize() {
		String[] comicWithoutLong = {"Mayor 5", "Mayor 160 lbs","Menor 40","H"};
		String target = "H";
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.readCSV("csv-files/simpsons.cvs");
			assertEquals(dt.prediction(comicWithoutLong),target);
		} catch (FileNotFoundException e) {
			fail("simpsons.cvs not found.");
		}
	}
	@Test(expected = Exception.class)
	public void testPredictRaisesAnExceptionShouldAValueWereNotPresentInPredictor() {
		String[] comicWithoutLong = {"Uy que raro xD", "Mayor 160 lbs","Menor 40"};
		String target = "H";
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.readCSV("csv-files/simpsons.cvs");
			assertEquals(dt.prediction(comicWithoutLong),target);
		} catch (FileNotFoundException e) {
			fail("simpsons.cvs not found.");
		}
	}
	@Test
	public void testPredictShouldPredictCorrectlySimpsonsCase() {
		String[] comic = {"Mayor 5", "Mayor 160 lbs","Menor 40"};
		String target = "H";
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.readCSV("csv-files/simpsons.cvs");
			assertEquals(dt.prediction(comic),target);
		} catch (FileNotFoundException e) {
			fail("simpsons.cvs not found.");
		}
	}

}
