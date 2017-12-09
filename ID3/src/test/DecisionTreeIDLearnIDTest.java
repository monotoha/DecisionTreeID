package test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import model.DecisionTreeID;

public class DecisionTreeIDLearnIDTest {
	
	@Test(expected = Exception.class)
	public void testReaderShouldRaiseAnExceptionWhenFormatIsNotCVS() 
			throws FileNotFoundException {
		DecisionTreeID dt = new DecisionTreeID();
		dt.learnDT("title");
		
	}
	@Test(expected = Exception.class)
	public void testReaderShouldRaiseAnExceptionWhenCVSIsNotFound() 
			throws FileNotFoundException {
		DecisionTreeID dt = new DecisionTreeID();
		dt.learnDT("WhereIsIt?.cvs");
		
	}
	
	@Test(expected = Exception.class)
	public void testReaderShouldRaiseAnExceptionWhenEmptyCVS() 
			throws FileNotFoundException {
		DecisionTreeID dt = new DecisionTreeID();
		dt.learnDT("Empty.cvs");
	}
	
	@Test(expected = Exception.class)
	public void testReaderShouldRaiseAnExceptionWhenUniqueColumnCVS() 
			throws FileNotFoundException {
		DecisionTreeID dt = new DecisionTreeID();
		dt.learnDT("OneCol.cvs");
	}
	
	@Test
	public void testReaderShouldSkipEmptyRows() {
		DecisionTreeID dt = new DecisionTreeID();
		String[][] caseEmptyRows = {{"Temperatura","Target"},{"Alta","Si"},{"Baja","No"}};
		
		List<List<String>> list;
		List<List<String>> target = Arrays.stream(caseEmptyRows, 0, caseEmptyRows.length)
				.map(p -> Arrays.asList(p))
				.collect(Collectors.toList());
		try {
			dt.learnDT("emptyRows.cvs");
			list = dt.getTable();
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("emptyRows.cvs not found.");
		}
	}
	
	@Test
	public void testReaderShouldSkipUncompleteLines() {
		DecisionTreeID dt = new DecisionTreeID();
		String[][] caseUncompleteLine = {{"Temperatura","Target"},{"Alta","Si"},{"Baja","No"}};
		
		List<List<String>> list;
		List<List<String>> target = Arrays.stream(caseUncompleteLine, 0, caseUncompleteLine.length)
				.map(p -> Arrays.asList(p))
				.collect(Collectors.toList());
		try {
			dt.learnDT("uncompleteLine.cvs");
			list = dt.getTable();
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("emptyRows.cvs not found.");
		}
	}
	
	
	@Test
	public void testReaderMissingValuesMapToUnknown() {
		//not sure
		fail("Not yet implemented");
	}
	
	@Test
	public void testReaderShouldGetTheCorrectSimpsonTable() {
		DecisionTreeID dt = new DecisionTreeID();
		String[][] caseSimpsons = 
			{
					{"Personaje","Longitud Pelo","Peso", "Edad", "Género"},
					{"Homer","Menor 5'","Mayor 160 lbs", "Menor 40", "H"},
					{"Marge","Mayor 5'","Menor 160 lbs", "Menor 40", "M"},
					{"Bart", "Menor 5","Menor 160 lbs", "Menor 40", "H"},
					{"Lisa", "Mayor 5","Menor 160 lbs", "Menor 40", "M"},
					{"Maggie", "Menor 5","Menor 160 lbs", "Menor 40", "M"},
					{"Abe","Menor 5'","Mayor 160 lbs", "Mayor 40", "H"},
					{"Selma","Mayor 5'","Menor 160 lbs", "Mayor 40", "M"},
					{"Otto","Mayor 5'","Mayor 160 lbs", "Menor 40", "H"},
					{"Krusty","Mayor 5'","Mayor 160 lbs", "Mayor 40", "H"}
			};
		
		List<List<String>> list;
		List<List<String>> target = Arrays.stream(caseSimpsons, 0, caseSimpsons.length)
				.map(p -> Arrays.asList(p))
				.collect(Collectors.toList());
		try {
			dt.learnDT("simpsons.cvs");
			list = dt.getTable();
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("simpsons.cvs not found.");
		}
	}
	
	@Test
	public void testReaderShouldGetTheCorrectPlayTennisTable() {
		DecisionTreeID dt = new DecisionTreeID();
		String[][] casePlayTennis = 
			{
					{"Day","Outlook","Temperature", "Humidity", "Wind","PlayTennis"},
					{"D1","Sunny","Hot", "High", "Weak", "No"},
					{"D2","Sunny","Hot", "High", "Strong", "No"},
					{"D3","Overcast","Hot", "High", "Weak", "Yes"},
					{"D4", "Rain","Mild", "High", "Weak", "Yes"},
					{"D5", "Rain","Cool", "Normal", "Weak", "Yes"},
					{"D6", "Rain","Cool", "Normal", "Strong", "No"},
					{"D7", "Overcast","Cool", "Normal", "Strong", "Yes"},
					{"D8","Sunny","Mild", "High", "Weak","No"},
					{"D9","Sunny","Cool", "Normal", "Weak","Yes"},
					{"D10","Rain","Mild", "Normal", "Weak","Yes"},
					{"D11","Sunny","Mild", "Normal", "Strong","Yes"},
					{"D12","Overcast","Mild", "High", "Strong","Yes"},
					{"D13","Overcast","Hot", "Normal", "Weak","Yes"},
					{"D14","Rain","Mild", "High", "Strong","No"}
			};
		
		List<List<String>> list;
		List<List<String>> target = Arrays.stream(casePlayTennis, 0, casePlayTennis.length)
				.map(p -> Arrays.asList(p))
				.collect(Collectors.toList());
		try {
			dt.learnDT("simpsons.cvs");
			list = dt.getTable();
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("emptyRows.cvs not found.");
		}
	}
	
	@Test
	public void testReaderShouldGetTheCorrectTitanicTable() {
		fail("Not yet implemented");
	}
}