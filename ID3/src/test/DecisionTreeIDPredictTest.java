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
		String[] comicWithoutLong = {"mayor 160 lbs","menor 40"};
		String target = "h";
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.learnID3("csv-files/simpsons.csv");
			assertEquals(dt.prediction(comicWithoutLong),target);
		} catch (FileNotFoundException e) {
			fail("simpsons.csv not found.");
		}
	}
	@Test
	public void testPredictShouldPredictIgnoringLastColumnIfSizeWereEqualsThanTableSize() {
		String[] comicWithoutLong = {"mayor 5", "mayor 160 lbs","menor 40","H"};
		String target = "h";
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.learnID3("csv-files/simpsons.csv");
			assertEquals(dt.prediction(comicWithoutLong),target);
		} catch (FileNotFoundException e) {
			fail("simpsons.csv not found.");
		}
	}
	@Test(expected = Exception.class)
	public void testPredictRaisesAnExceptionShouldAValueWereNotPresentInPredictor() {
		String[] comicWithoutLong = {"Uy que raro xD", "mayor 160 lbs","menor 40"};
		String target = "h";
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.learnID3("csv-files/simpsons.csv");
			assertEquals(dt.prediction(comicWithoutLong),target);
		} catch (FileNotFoundException e) {
			fail("simpsons.csv not found.");
		}
	}
	@Test
	public void testPredictShouldPredictCorrectlySimpsonsCase() {
		String[] comic = {"mayor 5", "mayor 160 lbs","menor 40"};
		String target = "h";
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.learnID3("csv-files/simpsons.csv");
			assertEquals(dt.prediction(comic),target);
		} catch (FileNotFoundException e) {
			fail("simpsons.csv not found.");
		}
	}

}
