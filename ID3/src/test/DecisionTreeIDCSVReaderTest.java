package test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import model.DecisionTreeID;

public class DecisionTreeIDCSVReaderTest {
	private final static String directory = "csv-files/";
	
	@Test(expected = Exception.class)
	public void testReaderShouldRaiseAnExceptionWhenFormatIsNotCVS() 
			throws FileNotFoundException {
		DecisionTreeID dt = new DecisionTreeID();
		dt.readCSV("title");
	}
	@Test(expected = Exception.class)
	public void testReaderShouldRaiseAnExceptionWhenCSVIsNotFound() 
			throws FileNotFoundException {
		DecisionTreeID dt = new DecisionTreeID();
		dt.readCSV("WhereIsIt?.csv");
	}
	
	@Test(expected = Exception.class)
	public void testReaderShouldRaiseAnExceptionWhenEmptyCSV() 
			 {
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.readCSV(directory+"Empty.csv");
		} catch (FileNotFoundException e) {
			fail("Empty.csv not found.");
		}
	}
	
	@Test(expected = Exception.class)
	public void testReaderShouldRaiseAnExceptionWhenUniqueRowCSV() 
			throws FileNotFoundException {
		DecisionTreeID dt = new DecisionTreeID();
		try {
			dt.readCSV(directory+"OneRow.csv");
		} catch (FileNotFoundException e) {
			fail("OneRow.csv not found.");
		}
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
			dt.readCSV(directory+"emptyRows.csv");
			list = dt.getTable();
			target = target.stream()
			.map(i -> i.stream().map(String::toLowerCase).collect(Collectors.toList()))
			.collect(Collectors.toList());
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("emptyRows.csv not found.");
		}
	}
	@Test(expected=Exception.class)
	public void testReaderShouldRaiseExceptionIfEmptyValuesInHeader() {
		DecisionTreeID dt = new DecisionTreeID();
		String[][] caseUncompleteLine = {{"Temperatura","Suelo","Target"},{"Alta","peridotita","Si"},{"Baja","caliza","No"}};
		
		List<List<String>> list;
		List<List<String>> target = Arrays.stream(caseUncompleteLine, 0, caseUncompleteLine.length)
				.map(p -> Arrays.asList(p))
				.collect(Collectors.toList());
		try {
			dt.readCSV(directory+"uncompleteHeader.csv");
			list = dt.getTable();
			target = target.stream()
			.map(i -> i.stream().map(String::toLowerCase).collect(Collectors.toList()))
			.collect(Collectors.toList());
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("uncompleteHeader.csv not found.");
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
			dt.readCSV(directory+"uncompleteLine.csv");
			list = dt.getTable();
			target = target.stream()
			.map(i -> i.stream().map(String::toLowerCase).collect(Collectors.toList()))
			.collect(Collectors.toList());
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("uncompleteLine.csv not found.");
		}
	}
	
	
	@Test
	public void testReaderShouldSkipMissingValues() {
		DecisionTreeID dt = new DecisionTreeID();
		String[][] caseMissing = 
			{
					{"Longitud Pelo","Peso", "Edad", "Genero"},
					{"Menor 5","Mayor 160 lbs", "Menor 40", "H"},
					{"Menor 5","Menor 160 lbs", "Menor 40", "H"},
					{"Mayor 5","Menor 160 lbs", "Menor 40", "M"},
					{"Menor 5","Mayor 160 lbs", "Mayor 40", "H"},
					{"Mayor 5","Mayor 160 lbs", "Menor 40", "H"},
					{"Mayor 5","Mayor 160 lbs", "Mayor 40", "H"}
			};
		
		List<List<String>> list;
		List<List<String>> target = Arrays.stream(caseMissing, 0, caseMissing.length)
				.map(p -> Arrays.asList(p))
				.collect(Collectors.toList());
		try {
			dt.readCSV(directory+"missingVals.csv");
			list = dt.getTable();
			target = target.stream()
			.map(i -> i.stream().map(String::toLowerCase).collect(Collectors.toList()))
			.collect(Collectors.toList());
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("missingVals.csv not found.");
		}
	}
	
	@Test
	public void testReaderShouldGetTheCorrectSimpsonTable() {
		DecisionTreeID dt = new DecisionTreeID();
		String[][] caseSimpsons = 
			{
					{"Longitud Pelo","Peso", "Edad", "Genero"},
					{"Menor 5","Mayor 160 lbs", "Menor 40", "H"},
					{"Mayor 5","Menor 160 lbs", "Menor 40", "M"},
					{"Menor 5","Menor 160 lbs", "Menor 40", "H"},
					{"Mayor 5","Menor 160 lbs", "Menor 40", "M"},
					{"Menor 5","Menor 160 lbs", "Menor 40", "M"},
					{"Menor 5","Mayor 160 lbs", "Mayor 40", "H"},
					{"Mayor 5","Menor 160 lbs", "Mayor 40", "M"},
					{"Mayor 5","Mayor 160 lbs", "Menor 40", "H"},
					{"Mayor 5","Mayor 160 lbs", "Mayor 40", "H"}
			};
		
		List<List<String>> list;
		List<List<String>> target = Arrays.stream(caseSimpsons, 0, caseSimpsons.length)
				.map(p -> Arrays.asList(p))
				.collect(Collectors.toList());
		try {
			dt.readCSV(directory+"simpsons.csv");
			list = dt.getTable();
			target = target.stream()
			.map(i -> i.stream().map(String::toLowerCase).collect(Collectors.toList()))
			.collect(Collectors.toList());
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("simpsons.csv not found.");
		}
	}
	
	@Test
	public void testReaderShouldGetTheCorrectPlayTennisTable() {
		DecisionTreeID dt = new DecisionTreeID();
		String[][] casePlayTennis = 
			{
					{"Outlook","Temperature", "Humidity", "Wind","PlayTennis"},
					{"Sunny","Hot", "High", "Weak", "No"},
					{"Sunny","Hot", "High", "Strong", "No"},
					{"Overcast","Hot", "High", "Weak", "Yes"},
					{"Rain","Mild", "High", "Weak", "Yes"},
					{"Rain","Cool", "Normal", "Weak", "Yes"},
					{ "Rain","Cool", "Normal", "Strong", "No"},
					{"Overcast","Cool", "Normal", "Strong", "Yes"},
					{"Sunny","Mild", "High", "Weak","No"},
					{"Sunny","Cool", "Normal", "Weak","Yes"},
					{"Rain","Mild", "Normal", "Weak","Yes"},
					{"Sunny","Mild", "Normal", "Strong","Yes"},
					{"Overcast","Mild", "High", "Strong","Yes"},
					{"Overcast","Hot", "Normal", "Weak","Yes"},
					{"Rain","Mild", "High", "Strong","No"}
			};
		
		List<List<String>> list;
		List<List<String>> target = Arrays.stream(casePlayTennis, 0, casePlayTennis.length)
				.map(p -> Arrays.asList(p))
				.collect(Collectors.toList());
		try {
			dt.readCSV(directory+"Tennis.csv");
			list = dt.getTable();
			target = target.stream()
			.map(i -> i.stream().map(String::toLowerCase).collect(Collectors.toList()))
			.collect(Collectors.toList());
			assertEquals(list,target);
		} catch (FileNotFoundException e) {
			fail("Tennis.csv not found.");
		}
	}
	
}
