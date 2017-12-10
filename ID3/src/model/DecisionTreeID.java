package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.mxgraph.view.mxGraph;

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
				r.forEach(x -> {
					row.add(x.isEmpty() ? "UNKNOWN" : x);
				});
				table.add(row);
			}

			System.out.println(table);

		} catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
	public List<List<String>> getTable() {
		return table;
	}

	public mxGraph drawDecisionTree() {
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
			Object v3 = graph.insertVertex(parent, null, "This", 500, 150, 80, 30);
			graph.insertEdge(parent, null, "Edge", v1, v2);
			graph.insertEdge(parent, null, "Edge", v1, v3);

		} finally {
			graph.getModel().endUpdate();
		}

		return graph;
	}

	public Object prediction(String[] registroCVS) {
		return null;
	}
}
