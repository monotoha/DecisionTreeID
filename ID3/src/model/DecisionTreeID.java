package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.mxgraph.view.mxGraph;

public class DecisionTreeID {
	private TaggedTree<String> model;
	/**
	 * table: FIRST ROW HEADERS, LAST COLUMN TARGET VALUES
	 */
	private final List<List<String>> table = new ArrayList<>(); 
	private Map<String, Set<String>> correspondenceVariable_Values; // An auxiliar variable
	private final static Comparator<Double[]> comparatorGain = 
			(m1,m2) -> m1[0].compareTo(m2[0]);
	CSVParser parser;

	public void learnID3(String ficheroCSV) throws FileNotFoundException {
		this.readCSV(ficheroCSV);
		this.model = buildID3Tree();
	}
	
	public void readCSV(String ficheroCSV) throws FileNotFoundException {
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
		} catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
	private TaggedTree<String> buildID3Tree() {
		boolean[] cases = new boolean[table.size()];
		boolean[] attributes = new boolean[table.get(0).size()];
		cases[0] = true;
		this.correspondenceVariable_Values = new HashMap<String,Set<String>>();
		IntStream.range(0, this.table.get(0).size())
		.forEach(
				j -> this.correspondenceVariable_Values
				.put(table.get(0).get(j), getValuesGivenColumn(j))
				);
		return buildID3Tree(cases,attributes);
	}


	public Set<String> getValuesGivenColumn(Integer j) {
		return IntStream.range(1, this.table.size())
				.boxed()
				.map(i -> this.table.get(i).get(j))
				.collect(Collectors.toSet());
	}


	private TaggedTree<String> buildID3Tree(boolean[] cases, boolean[] attributes) {
		TaggedTree<String> t;
		Set<String> values;
		Map<String,TaggedTree<String>> sons;
		int indexNodeMaxGain;
		boolean[] newAttributes;
		String leafNode = leafNode(cases,attributes), rootNodeValue;
		
		if (leafNode !=null) {
			t = new TaggedTree<>(leafNode);
		} else {
			indexNodeMaxGain = this.getIndexMaxGain(cases, attributes);
			rootNodeValue = this.table.get(0).get(indexNodeMaxGain);
			values = this.correspondenceVariable_Values.get(rootNodeValue);
			sons = new HashMap<>();
			newAttributes = Arrays.copyOf(attributes,attributes.length);
			newAttributes[indexNodeMaxGain] = true;
			values.stream().forEach(i -> sons.put(i, buildID3Tree(newCases(i,indexNodeMaxGain,cases),newAttributes)));
			t = new TaggedTree<>(rootNodeValue,sons);
		}
		return t;
	}
	
	private boolean[] newCases(String value,int j, boolean[] cases) {
		boolean[] res = Arrays.copyOf(cases, cases.length);
		IntStream.range(1, this.table.size())
		.forEach(i -> res[i] = res[i] || !this.table.get(i).get(j).equals(value));
		return res;
	}


	private int getIndexMaxGain(boolean[] cases, boolean[] attributes) {
		return ganancia(cases,attributes,this.table.get(0).size()-1);
	}
	
	/**
	 * 
	 * @return List formed by last column of the table.
	 */
	public List<String> getTargetColumn() {
		return IntStream.range(0, this.table.size())
				.boxed()
				.map(i -> this.table.get(i).get(this.table.get(i).size() -1))
				.collect(Collectors.toList());
	}
	/**
	 * Get the node value of the tree if it is a leaf node.
	 * Otherwise null will be returned.
	 * There are 2 alternatives scenarios for a node to be leaf:
	 * 1. If the table has no more variables for the model to learn.
	 * The most frequent value in the target will be returned.
	 * 2. If by cases discrimination there is only ONE target value.
	 * That one value will be returned.
	 * @param cases : What cases should not be considered
	 * @param attributes : What attributes should not be considered
	 * @return node value or null
	 */
	private String leafNode(boolean[] cases, boolean[] attributes) {
		Map<String, Integer> occurrences = new HashMap<>();
		boolean isLeaf = true;
		String current;
		List<String> target = getTargetColumn();
		Iterator<String> iter = IntStream.range(1, target.size())
				.filter(i -> !cases[i]).boxed()
				.map(i -> target.get(i))
				.iterator();
		boolean allVarsChecked = IntStream.range(0, attributes.length - 1)
				.boxed()
				.map(i -> attributes[i])
				.reduce(true, (a,b) -> a && b);
		while (iter.hasNext() && isLeaf) {
			if (occurrences.size() > 1 && !allVarsChecked) {
				isLeaf = false;
			} else {
				current = iter.next();
				if (occurrences.containsKey(current)) {
					 occurrences.put(current, occurrences.get(current));
				} else {
					occurrences.put(current, 1);
				}
			}
		}
		return isLeaf? occurrences.entrySet().stream()
		.max((m1,m2) -> m1.getValue().compareTo(m2.getValue()))
		.get().getKey()		:	null;
	}


	
	public List<List<String>> getTable() {
		return table;
	}
	public TaggedTree<String> getModel() {
		return this.model;
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
	/**
	 * 
	 * @param row : what cases should be considered
	 * @param col : what variables should be considered
	 * @param res : Target index
	 * @return
	 */
    public int ganancia(boolean[] row,boolean[] col,int res)
    {
        double[] ganancias = new double[col.length]; // for each variable there is one gain
        double[] classAmount;
        List<String> valuesRes = values(res,row); // set containing the target values
        double entropiaRes = entropiaRes(res,row,valuesRes); // entropy target
        List<String> values; // helper variable containing the values of a given index variable
        for(int i=0;i<col.length;i++)
        {
            values = values(i,row); // this might be troublesome
            double total=0; // number of cases to be considered
            ganancias[i] = entropiaRes; // first value is target entropy
            
            if(i!=res) // if i is target, gain won't be calculated
            {
                classAmount = new double[values.size()]; // for each variable value one class amount.
                
                for(int j=0;j<row.length;j++)
                    if(!row[j]) // checks if the case i should be considered
                    {
                        classAmount[values.indexOf(table.get(j).get(i))]++; 
                        // it finds current value in values set and then takes awareness
                        total++;
                    }       
                double[] entropia = entropia(res,i,row,valuesRes); // for each variable's value -> one entropy
                for(int j =0;j<entropia.length;j++)
                {
                    ganancias[i]-=(classAmount[j]/total)*entropia[j];
                }
                
            }
        }
        int index =-1;
        double max=-1;
        for(int i=0;i<ganancias.length;i++)
        {
            if(ganancias[i]>max && i != res && !col[i])
            {
                max=ganancias[i];
                index = i;
            }
        }
        
        return index;
    }
    
    public List<String> values(int col,boolean[] row)
    {
        List<String> values =new ArrayList<>();
        for(int j=0;j<row.length;j++)
        {
            if(!values.contains(table.get(j).get(col)) && !row[j] && j!=0)
            {
                values.add(table.get(j).get(col));
            }
            
        }
        return values;
    }
    
    
    
    public double entropiaRes(int col,boolean[] row,List<String> values)
    {
        double entropia =0;
        double total = 0;
        double[] classAmount;
        
        classAmount = new double[values.size()];
        
        for(int j=0;j<row.length;j++)
            if(!row[j])
            {
                classAmount[values.indexOf(table.get(j).get(col))]++;
                total++;
            }
        for(int j=0;j<classAmount.length;j++)
        {
            if(classAmount[j]>0)
                entropia-=(classAmount[j]/total)*(Math.log(classAmount[j]/total)/Math.log(2));
            else
            	entropia-=0;
             // el logaritmo tiene que ser base 2, no e. TODO
        }
        return entropia;
    }
    
    
    public double[] entropia(int colRes,int col,boolean[] row,List<String> valuesRes)
    {
        List<String> values = values(col,row);
        double[] entropia=new double[values.size()];
        double[] total =new double[values.size()];
        double[][] classAmount = new double[values.size()][valuesRes.size()];
        for(int j=0;j<row.length;j++)
        {
            if(!row[j])
            {
                classAmount[values.indexOf(table.get(j).get(col))][valuesRes.indexOf(table.get(j).get(colRes))]++;
                total[values.indexOf(table.get(j).get(col))]++;
            }
        }
        
        for(int i=0; i<classAmount.length;i++)
        {
            for(int j=0;j<classAmount[0].length;j++)
            {   
                if(classAmount[i][j]>0)
                    entropia[i]-=(classAmount[i][j]/total[i])*(Math.log(classAmount[i][j]/total[i])/Math.log(2));
                else
                	entropia[i]-=0;
                    // el logaritmo tiene que ser en base 2, no e. TODO
                
            }
        }
        
        
        return entropia;
    }
}
