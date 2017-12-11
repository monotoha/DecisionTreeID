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
			this.model = buildID3Tree();
		} catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
	
	private TaggedTree<String> buildID3Tree() {
		boolean[] cases = new boolean[table.size()];
		boolean[] attributes = new boolean[table.get(0).size()];
		this.correspondenceVariable_Values = new HashMap<String,Set<String>>();
		IntStream.range(0, this.table.get(0).size())
		.forEach(
				j -> this.correspondenceVariable_Values
				.put(table.get(0).get(j), getValuesGivenColumn(j))
				);
		return buildID3Tree(cases,attributes);
	}


	private Set<String> getValuesGivenColumn(Integer j) {
		return IntStream.range(0, this.table.size())
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
			values.forEach(i -> sons.put(i, buildID3Tree(newCases(i,indexNodeMaxGain,cases),newAttributes)));
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
		double entropy = entropy(cases);
		return IntStream.range(0, table.get(0).size() - 1)
		.boxed()
		.filter(j -> !attributes[j])
		.map(j -> gainGivenColumnAndCases(j,cases,entropy))
		.max(comparatorGain)
		.get() // if not present throws NoSuchElementException
		[1].intValue();
	}
	
	/**
	 * This method gets the variable's Gain.
	 * @param j : Index of the variable.
	 * @param cases : A boolean array telling if a case shouldn't be considered.
	 * @param entropy : Entropy value of the target.
	 * @return A Double[] size 2 formed by
	 *  the variable index j (position 1) and its gain (position 0).
	 */
	private Double[] gainGivenColumnAndCases(Integer j, boolean[] cases, double entropy) {
		// TODO Auto-generated method stub
		Double[] res = new Double[2];
		res[0] = 0.5;
		res[1] = j.doubleValue();
		return res;
	}

	/**
	 * Gets entropy of the target.
	 * @param cases : A boolean array telling if a case shouldn't be considered.
	 * @return : Entropy value
	 */
	private double entropy(boolean[] cases) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Given a variable and the value gets its entropy.
	 * Note that this is a posterior.
	 * @param j : Variable's index.
	 * @param cases : What cases shouldn't be considered.
	 * @param valueGiven : what value the variable takes.
	 * @return such entropy
	 */
	private double entropyConditioned(Integer j, boolean[] cases, String valueGiven) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 
	 * @return List formed by last column of the table.
	 */
	private List<String> getTargetColumn() {
		return IntStream.range(0, this.table.size())
				.boxed()
				.map(i -> this.table.get(i).get(this.table.size() -1))
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
		boolean isLeaf = false;
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
    private int ganancia(boolean[] row,boolean[] col,int res)
    {
        int[] ganancias = new int[col.length];
        int[] classAmount;
        List<String> valuesRes = values(res,row);
        int entropiaRes = entropiaRes(res,row,valuesRes);
        List<String> values;
        for(int i=0;i<col.length;i++)
        {
            values = values(i,row);
            int total=0;
            ganancias[i] = entropiaRes;
            if(i!=res)
            {
                
                
                classAmount = new int[values.size()+1];
                
                for(int j=0;j<row.length;j++)
                    if(!row[j])
                    {
                        classAmount[values.indexOf(table.get(j).get(i))]++;
                        total++;
                    }       
                int[] entropia = entropia(res,i,row,valuesRes);
                for(int j =0;j<entropia.length;j++)
                {
                    ganancias[i]-=(classAmount[j]/total)*entropia[j];
                }
                
            }
        }
        int index =0;
        int max=0;
        for(int i=0;i<ganancias.length;i++)
        {
            if(ganancias[i]>max)
            {
                max=ganancias[i];
                index = i;
            }
        }
        
        return index;
    }
    
    private List<String> values(int col,boolean[] row)
    {
        List<String> values =new ArrayList<>();
        for(int j=0;j<row.length;j++)
            if(!values.contains(table.get(j).get(col)) && !row[j])
            {
                values.add(table.get(j).get(col));
            }
        return values;
    }
    
    
    
    private int entropiaRes(int col,boolean[] row,List<String> values)
    {
        int entropia =0;
        int total = 0;
        int[] classAmount;
        
        classAmount = new int[values.size()+1];
        
        for(int j=0;j<row.length;j++)
            if(!row[j])
            {
                classAmount[values.indexOf(table.get(j).get(col))]++;
                total++;
            }
        for(int j=0;j<classAmount.length;j++)
        {
            try
            {
                entropia-=(classAmount[j]/total)*Math.log(classAmount[j]/total);
            }
            catch(Exception e)
            {
                entropia -= 0;
            }
        }
        return entropia;
    }
    
    private int[] entropia(int colRes,int col,boolean[] row,List<String> valuesRes)
    {
        List<String> values = values(col,row);
        int[] entropia=new int[values.size()+1];
        int[] total =new int[values.size()+1];
        int[][] classAmount = new int[values.size()+1][valuesRes.size()+1];
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
                try
                {
                    entropia[i]-=(classAmount[i][j]/total[i])*Math.log(classAmount[i][j]/total[i]);
                }
                catch(Exception e)
                {
                    entropia[i]-=0;
                }
            }
        }
        
        
        return entropia;
    }
}
