package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.DecisionTreeID;

public class DecisionTreeIDEntropyTest {
	@Test
	public void testEntropyResFarmaco() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/farmaco.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		row[0] = true;
		double val = t.entropiaRes(list.get(0).size()-1, row, t.values(list.get(0).size()-1, row));
		double valEst = 0.863121;
		assertEquals(valEst,val,0.0001);
	}
	
	@Test
	public void testShouldGetAllEntropiesForIndiceColesterolInFarmacoCSV() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/farmaco.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		row[0] = true;
		int indiceColesterolIndexVar = 2;
		double[] val = t.entropia(list.get(0).size()-1,indiceColesterolIndexVar, row, t.values(list.get(0).size()-1, row));
		double[] valEst = {0.764204,0.9709505};
		assertArrayEquals(valEst,val,0.0001);
	}
	
	@Test
	public void testIndexMaxGainShouldBePresionArterialIndexUsingFarmacoCSV() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/farmaco.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		boolean[] col = new boolean[list.get(0).size()];
		row[0] = true;
		int ind = t.ganancia(row,col,list.get(0).size()-1);
		assertEquals(0,ind);
	}

	@Test
	public void testIndexMaxGainSecondIterationShouldBeWeightIndexSimpsonsCSV() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/simpsons.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		boolean[] col = new boolean[list.get(0).size()];
		Arrays.fill(row, true);
		row[1] = false; row[3] = false; row[5] = false; row[6] = false;
		col[0] = true;
		int val = t.ganancia(row,col,list.get(0).size()-1);
		int valEst = 1;
		assertEquals(valEst,val,0.0001);
	}
	
	@Test
	public void testIndexMaxGainFirstIterationShouldBeHairLengthIndexSimpsonsCSV() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/simpsons.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		boolean[] col = new boolean[list.get(0).size()];
		row[0] = true;
		col[0] = true;
		int val = t.ganancia(row,col,list.get(0).size()-1);
		int valEst = 1;
		assertEquals(valEst,val);
	}
	
	@Test
	public void testEntropySecondIterationWeightSimpsons() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/simpsons.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		Arrays.fill(row, true);
		row[1] = false; row[3] = false; row[5] = false; row[6] = false;
		double[] val = t.entropia(list.get(0).size()-1,1, row, t.values(list.get(0).size()-1, row));
		double[] valEst = {0,1};
		assertArrayEquals(valEst,val,0.0001);
	}
	
	@Test
	public void testEntropyFirstIterationAllVarsSimpsons() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/simpsons.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		row[0] = true;
		double[] val;
		double[][] valEst = {
				{0.8113,0.97095},
				{0,0.7219},
				{1,0.9183}
				};
		for (int i = 0; i<list.get(0).size()-1;i++) {
			val = t.entropia(list.get(0).size()-1,i, row, t.values(list.get(0).size()-1, row));
			assertArrayEquals(valEst[i],val,0.0001);
		}
	}
	
	@Test
	public void testEntropyResSecondIterationSimpsons() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/simpsons.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		Arrays.fill(row, true);
		row[1] = false; row[3] = false; row[5] = false; row[6] = false;
		double val = t.entropiaRes(list.get(0).size()-1, row, t.values(list.get(0).size()-1, row));
		double valEst = 0.81127;
		assertEquals(valEst,val,0.001);
	}
	
	@Test
	public void testEntropyResFirstIterationSimpsons() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/simpsons.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		row[0] = true;
		double val = t.entropiaRes(list.get(0).size()-1, row, t.values(list.get(0).size()-1, row));
		double valEst = 0.9911;
		assertEquals(valEst,val,0.001);
	}
	
	@Test
	public void testEntropyResTennisFirstIteration() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		row[0] = true;
		double val = t.entropiaRes(list.get(0).size()-1, row, t.values(list.get(0).size()-1, row));
		double valEst = 0.9403;
		assertEquals(valEst,val,0.001);
	}
	
	@Test
	public void testEntropyTennisFirstIterationForAllVariables() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		List<String> target = t.values(list.get(0).size()-1, row);
		row[0] = true;
		double[] entropys;
		double[][] entropysEst = {
				{0.9710,0,0.9710},
				{1,0.9183,0.8113},
				{0.9852,0.5917},
				{0.8113,1}
								};
		for (int j = 0; j<list.get(0).size()-1;j++) {
			entropys = t.entropia(list.get(0).size()-1, j, row, target);
			assertArrayEquals(entropysEst[j],entropys,0.001);
		}
	}
	
	@Test
	public void testIndexMaxGainTennisFirstIterationShouldBeOutlook() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		boolean[] col = new boolean[list.get(0).size()];
		row[0] = true;
		int ind;
		int indEst=0; // Outlook index
		ind = t.ganancia(row, col, list.get(0).size()-1);
		assertEquals(ind,indEst);
	}
	
	@Test
	public void testEntropyResTennisSecondIteration() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		Arrays.fill(row, true);
		row[1] = false; row[2] = false; row[8] = false;
		row[9] = false; row[11]= false;
		double val = t.entropiaRes(list.get(0).size()-1, row, t.values(list.get(0).size()-1, row));
		double valEst = 0.9710;
		assertEquals(valEst,val,0.001);
	}
	
	@Test
	public void testEntropyTennisSecondIterationForAllVariables() throws FileNotFoundException {
		int cont = 0;
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		
		
		
		List<String> target = t.values(list.get(0).size()-1, row);
		
		
		boolean[] col = new boolean[list.get(0).size()];
		col[0] = true; col[list.get(0).size()-1] = true;
		Arrays.fill(row, true);
		row[1] = false; row[2] = false; row[8] = false;
		row[9] = false; row[11]= false;
		double[] entropys;
		double[][] entropysEst = {
				{0,0,0},
				{0,1,0},
				{0,0},
				{0.9183,1},
								};
		for (int j = 0; j<list.get(0).size();j++) {
			if (!col[j]) {
			entropys = t.entropia(list.get(0).size()-1, j, row, target);
			assertArrayEquals(entropysEst[j],entropys,0.001);
			}
		
		}
	}
	
	@Test
	public void testIndexMaxGainTennisSecondIterationShouldBeHumidity() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		boolean[] col = new boolean[list.get(0).size()];
		Arrays.fill(row, true);
		row[1] = false; row[2] = false; row[8] = false;
		row[9] = false; row[11]= false;
		col[0] = true; col[list.get(0).size()-1] = true;
		int ind;
		int indEst=2; // Humidity index
		ind = t.ganancia(row, col, list.get(0).size()-1);
		assertEquals(ind,indEst);
	}
	
	
	
	@Test
	public void testEntropyResTennisThirdIteration() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		Arrays.fill(row, true);
		row[4] = false; row[5] = false; row[6] = false;
		row[10] = false; row[14]= false;
		double val = t.entropiaRes(list.get(0).size()-1, row, t.values(list.get(0).size()-1, row));
		double valEst = 0.9710;
		assertEquals(valEst,val,0.001);
	}
	
	@Test
	public void testEntropyTennisThirdIterationForAllVariables() throws FileNotFoundException {
		int cont = 0;
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		boolean[] col = new boolean[list.get(0).size()];
		List<String> target = t.values(list.get(0).size()-1, row);
		col[0] = true; col[list.get(0).size()-1] = true;
		Arrays.fill(row, true);
		row[4] = false; row[5] = false; row[6] = false;
		row[10] = false; row[14]= false;
		double[] entropies;
		double[][] entropysEst = {
				{0,0,0},
				{0.9183,1},
				{1,0.9183},
				{0,0},
								};
		for (int j = 0; j<list.get(0).size();j++) {
			if (!col[j]) {
			entropies = t.entropia(list.get(0).size()-1, j, row, target);
			assertArrayEquals(entropysEst[j],entropies,0.001);
			}
		}
	}
	
	@Test
	public void testIndexMaxGainTennisThirdIterationShouldBeWind() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/Tennis.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		boolean[] col = new boolean[list.get(0).size()];
		Arrays.fill(row, true);
		row[4] = false; row[5] = false; row[6] = false;
		row[10] = false; row[14]= false;
		col[0] = true; col[list.get(0).size()-1] = true;
		int ind;
		int indEst=3; // Wind index
		ind = t.ganancia(row, col, list.get(0).size()-1);
		assertEquals(ind,indEst);
	}
	

	@Test
	public void testEntropyShouldWorkIfThereWereAnyZeroesInNumerator() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/entropyZeros.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		row[0] = true;
		double[] val = t.entropia(list.get(0).size()-1,0, row, t.values(list.get(0).size()-1, row));
		double[] valEst = {0,0};
		assertArrayEquals(valEst,val,0.0001);
	}
	
	@Test
	public void testEntropiesEmptyRowsShouldGetNoEntropies() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		t.readCSV("csv-files/entropyZeros.csv");
		List<List<String>> list = t.getTable();
		boolean[] row = new boolean[list.size()];
		Arrays.fill(row, true);
		double[] val = t.entropia(list.get(0).size()-1,0, row, t.values(list.get(0).size()-1, row));
		double[] valEst = {};
		assertArrayEquals(valEst,val,0.0001);
	}

}
