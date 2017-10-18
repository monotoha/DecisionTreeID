package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DecisionTreeID {
	private final List<List<String>> table = new ArrayList<>();
	

	CSVParser parser;
	
	public void learnDT(String ficheroCSV) throws FileNotFoundException {
		
		Reader reader = new BufferedReader(new FileReader(ficheroCSV));
		
		try {
			parser = new CSVParser(reader, CSVFormat.DEFAULT);
			List<CSVRecord> records = parser.getRecords();
			
			for (CSVRecord r : records) {
				List<String> row = new ArrayList<>();
				r.forEach(x -> {row.add(x.isEmpty() ? "UNKNOWN" : x);});
				table.add(row);
			}
			
			System.out.println(table);
			
		} catch (IOException e) {
			System.out.println("IOException");
		}
	}

	public void drawDecisionTree() {
		
	}

	public Object prediction(String[] registroCVS) {
		return null;
	}
}
