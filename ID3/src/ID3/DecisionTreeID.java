package ID3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DecisionTreeID
{
	private TaggedTree<String> model;
	/**
	 * table: FIRST ROW HEADERS, LAST COLUMN TARGET VALUES
	 */
	private final List<List<String>> table = new ArrayList<>(); 
	private Map<String, Set<String>> correspondenceVariable_Values; // An auxiliar variable
	private final static Comparator<Double[]> comparatorGain = 
			(m1,m2) -> m1[0].compareTo(m2[0]);
	public void learnDT(String ficheroCVS) throws FileNotFoundException
	{	
		TaggedTree<String> t;
		Scanner s = new Scanner(new File(ficheroCVS));
		//Assuming that we aren't going to use the " character
		s.useDelimiter(",\\n");
		
		while(s.hasNextLine())
		{
			List<String> innerList = new ArrayList<>();
			
			Scanner scannerLine = new Scanner(s.nextLine());
			while(scannerLine.hasNext())
			{
				innerList.add(scannerLine.next());
			}
			table.add(innerList);
		}
		
		System.out.println(table);
		this.model = buildID3Tree();
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
		String leafNode = leafNode(cases), rootNodeValue;
		
		if (leafNode !=null) {
			t = new TaggedTree<>(leafNode);
		} else {
			indexNodeMaxGain = this.getIndexMaxGain(cases, attributes);
			rootNodeValue = this.table.get(0).get(indexNodeMaxGain);
			values = this.correspondenceVariable_Values.get(rootNodeValue);
			sons = new HashMap<>();
			newAttributes = Arrays.copyOf(attributes,attributes.length);
			newAttributes[indexNodeMaxGain] = true;
			values.forEach(i -> sons.put(i, buildID3Tree(newCases(i,indexNodeMaxGain,cases.length),newAttributes)));
			t = new TaggedTree<>(rootNodeValue,sons);
		}
		return t;
	}
	
	private boolean[] newCases(String value,int j, int numCases) {
		boolean[] res = new boolean[numCases];
		IntStream.range(1, this.table.size())
		.forEach(i -> res[i] = !this.table.get(i).get(j).equals(value));
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
	 * @param cases : What cases should not be considered
	 * @return node value or null
	 */
	private String leafNode(boolean[] cases) {
		List<String> target = getTargetColumn();
		List<String> nodes = IntStream.range(1,target.size())
				.boxed()
				.filter(i -> !cases[i])
				.map(i -> target.get(i))
				.limit(2)
				.collect(Collectors.toList());
		return nodes.size() == 1? nodes.get(0) : null;
	}


	public List<List<String>> getTable() {
		return table;
	}


	public void drawDecisionTree()
	{

	}
	public Object prediction(String[] registroCVS)
	{
		return null;
	}
	
}
